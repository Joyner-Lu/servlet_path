package com.joyner.servlet;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
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
    /**
     * The Carriage Return ASCII character value.
     */
    public static final byte CR = 0x0D;

    /**
     * The Line Feed ASCII character value.
     */
    public static final byte LF = 0x0A;

    /**
     * The dash (-) ASCII character value.
     */
    public static final byte DASH = 0x2D;

    /**
     * A byte sequence that precedes a boundary (<code>CRLF--</code>).
     */
    protected static final byte[] BOUNDARY_PREFIX = {CR, LF, DASH, DASH};

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String lastName = req.getParameter("lastname");

        Enumeration<String> list = req.getParameterNames();
        while (list.hasMoreElements()) {
            String item = list.nextElement();
            System.out.println("item:" + item);
        }
        System.out.println("------1");

        boolean isMultipart = ServletFileUpload.isMultipartContent(req);
        System.out.println("file:" + isMultipart);
        String firstname = req.getParameter("firstname");
        System.out.println("firstname:" + firstname);


        // Create a factory for disk-based file items
        DiskFileItemFactory factory = new DiskFileItemFactory();

// Configure a repository (to ensure a secure temp location is used)
        ServletContext servletContext = this.getServletConfig().getServletContext();
        File repository = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
        factory.setRepository(repository);

/*// Create a new file upload handler
        ServletFileUpload upload = new ServletFileUpload(factory);

// Parse the request
        try {
            List<FileItem> items = upload.parseRequest(req);
        } catch (Exception ex) {
            System.out.println("fdfsd");
        }*/


        rawProcess(req, resp);

    }

    private void rawProcess(HttpServletRequest req, HttpServletResponse resp) {
        try {
            String contentType = req.getContentType();
            String boundary = contentType.split("boundary=")[1];
            System.out.println(boundary);
            int boundaryLen = boundary.getBytes().length + BOUNDARY_PREFIX.length;

            //获取

            //1.获取boundary
            //2.获取request body

            InputStream is = req.getInputStream();
            byte it = (byte) is.read();
            System.out.println(it);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }


}
