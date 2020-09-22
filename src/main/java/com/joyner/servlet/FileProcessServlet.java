package com.joyner.servlet;

import com.joyner.common.algorithm.KMP;
import com.joyner.entity.FileItemEntity;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;

/**
 * <pre>
 *
 * </pre>
 *
 * @author 陆清云 luqingyun@foresee.cn
 * @version 1.00.00
 *
 * <pre>
 * 修改记录
 * 修改后版本: 修改人： 修改日期: 修改内容:
 * </pre>
 */
@WebServlet(name = "FileProcessServlet", urlPatterns = {"/FileProcessServlet.do"}, asyncSupported = true)
public class FileProcessServlet extends HttpServlet {

    private List<FileItemEntity> fileItemEntityList = null;

    /**
     * The Carriage Return ASCII character value.
     */
    public static final byte CR = 0x0D;//13

    /**
     * The Line Feed ASCII character value.
     */
    public static final byte LF = 0x0A;//10

    /**
     * The dash (-) ASCII character value.
     */
    public static final byte DASH = 0x2D;

    /**
     * A byte sequence that precedes a boundary (<code>--</code>).
     */
    protected static final byte[] BOUNDARY_PREFIX = {DASH, DASH};
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String lastName = req.getParameter("lastname");
        fileItemEntityList = new ArrayList<>();
        Enumeration<String> list = req.getParameterNames();
        while (list.hasMoreElements()) {
            String item = list.nextElement();
            System.out.println("item:" + item);
        }


        /*FileInputStream fileInputStream = new FileInputStream("C:\\Users\\62358\\Desktop\\指令.png");
        System.out.println(fileInputStream.available());

        rawProcess(req, resp);
        for(FileItemEntity fileItemEntity : fileItemEntityList) {
            if (!fileItemEntity.isFormField()) {
                FileOutputStream fileOutputStream = new FileOutputStream("D:/" + fileItemEntity.getFileName());
                fileOutputStream.write(fileItemEntity.getItemBytes());
                fileOutputStream.flush();
                fileInputStream.close();
            }
        }
*/
    }

    private void rawProcess(HttpServletRequest req, HttpServletResponse resp) {
        try {
            String contentType = req.getContentType();
            String boundary = "--" + contentType.split("boundary=")[1];
            byte[] boundaryBytes = boundary.getBytes();
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            int fromIndex = boundary.getBytes().length;
            int boundaryLen = boundary.getBytes().length;
            InputStream is = req.getInputStream();
            byte[] buffer = new byte[1024];
            int i = 0;
            while ((i = is.read(buffer)) != -1) {
                byteArrayOutputStream.write(buffer,0, i);
            }

            byte[] bytes = byteArrayOutputStream.toByteArray();

            //跳过第一个boundary
            int index = KMP.indexOfByte(bytes, boundaryBytes, fromIndex);
            while (index != -1) {
                byte[] itemBytes = Arrays.copyOfRange(bytes, fromIndex, index);
                processItemBytes(itemBytes);
                //设置下次搜素的开始位置
                fromIndex = index + boundaryLen;
                index = KMP.indexOfByte(bytes, boundaryBytes, fromIndex);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void processItemBytes(byte[] itemBytes) {
        FileItemEntity fileItem = new FileItemEntity();
        int CR_LF_LEN = 2;
        int fromIndex = CR_LF_LEN;
        byte[] crLfBytes = new byte[]{CR, LF};

        //跳过第一个CR LF
        boolean beginReadContent = false;
        int index = KMP.indexOfByte(itemBytes, crLfBytes, fromIndex);
        int i = 0;
        while (index != -1) {
            byte[] bytes = Arrays.copyOfRange(itemBytes, fromIndex, index);
            //读取其他信息
            if (i == 0) {
                //读取Content-Disposition: form-data; name="lastname"
                String contentDisposition = new String(bytes);
                String[] contentDispositionArr = contentDisposition.split(";");
                fileItem.setFieldName(contentDispositionArr[1].split("=")[1].replaceAll("\"", ""));
                if (contentDispositionArr.length > 2) {
                    fileItem.setFormField(false);
                    fileItem.setFileName(contentDispositionArr[2].split("=")[1].replaceAll("\"", ""));
                }
            } else if (i == 1 && fromIndex != index) {
                //获取content-type
                String contentType = new String(bytes);
                String type = contentType.split(":")[1];
                fileItem.setContentType(type.trim());
            }

            if (fromIndex == index) {
                //开始直接读取内容，并结束
                bytes = Arrays.copyOfRange(itemBytes, fromIndex + CR_LF_LEN, itemBytes.length - CR_LF_LEN);
                fileItem.setItemBytes(bytes);
                index = -1;
            } else {
                fromIndex = index + CR_LF_LEN;
                index = KMP.indexOfByte(itemBytes, crLfBytes, fromIndex);
            }

            i++;
        }

        fileItemEntityList.add(fileItem);

    }

}
