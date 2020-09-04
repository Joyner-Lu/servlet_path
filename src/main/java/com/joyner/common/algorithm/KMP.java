package com.joyner.common.algorithm;

import org.apache.commons.io.output.ByteArrayOutputStream;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Arrays;

/**
 * <pre>
 * KMP算法，模式匹配串算法
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
public class KMP {


    /**
     * 查询是否subStr存在于str中，如果存在，则返回匹配的第一个index的值
     *
     * @param str
     * @param subStr
     * @return 返回-1表示未查询掉
     */
    public static int indexOfStr(String str, String subStr) {
        if (str.length() < 1 || subStr.length() < 1 || subStr.length() > str.length()) {
            return -1;
        }
        //获取前缀数组
        int i = 0;
        int[] next = getNext(subStr);
        int len = str.length();
        int subLen = subStr.length();

        for (int j = 0; j < len; j++) {
            if ((len - j) < subLen) {
                //剩余的宽度不足以和子串比较
                return -1;
            }

            char c = str.charAt(j);
            if (i == 0) {
                char firstChar = subStr.charAt(0);
                if (c != firstChar) {
                    continue;
                }
                if (subLen == 1) {
                    //证明子串只有一个
                    return j;
                }
                //取下一个字符
                j++;
                i++;

                c = str.charAt(j);
            }

            //比较剩下的位
            for (; i < subLen; i++) {

                char subC = subStr.charAt(i);
                if (subC != c) {
                    if (i == 0) {
                        //取主串的下一个进行比较
                        break;
                    }
                    //与主串的当前位进行比较
                    int pos = next[i + 1];
                    //修改位置（减2是因为for里面有+1）
                    i = pos - 2;
                    continue;
                } else {

                    if (i == (subLen - 1)) {
                        //比较完毕，返回匹配的索引
                        return j - subLen + 1;
                    }
                    //相等进行下一位比较
                    //取主串的下一个字符
                    j++;
                    c = str.charAt(j);
                    continue;
                }


            }
        }


        return -1;
    }

    /**
     * 查询是否subBytes存在于bytes中，如果存在，则返回匹配的第一个index的值
     *
     * @param bytes
     * @param subBytes
     * @return 返回-1表示未查询掉
     */
    public static int indexOfByte(byte[] bytes, byte[] subBytes) {

        if (bytes.length < 1 || subBytes.length < 1 || subBytes.length > bytes.length) {
            return -1;
        }

        //获取前缀数组
        int i = 0;
        int[] next = getNext(subBytes);
        int len = bytes.length;
        int subLen = subBytes.length;

        for (int j = 0; j < len; j++) {
            if ((len - j) < subLen) {
                //剩余的宽度不足以和子串比较
                return -1;
            }

            byte b = bytes[j];
            if (i == 0) {
                byte firstByte = subBytes[0];
                if (b != firstByte) {
                    continue;
                }
                if (subLen == 1) {
                    //证明子串只有一个
                    return j;
                }

                //取下一个字符
                j++;
                i++;
                b = bytes[j];
            }

            //比较剩下的位
            for (; i < subLen; i++) {

                byte subByte = subBytes[i];
                if (subByte != b) {
                    if (i == 0) {
                        //取主串的下一个进行比较
                        break;
                    }
                    //与主串的当前位进行比较
                    int pos = next[i + 1];
                    //修改位置（减2是因为for里面有+1）
                    i = pos - 2;
                    continue;
                } else {

                    if (i == (subLen - 1)) {
                        //比较完毕，返回匹配的索引
                        return j - subLen + 1;
                    }
                    //相等进行下一位比较
                    //取主串的下一个字符
                    j++;
                    b = bytes[j];
                    continue;
                }


            }
        }


        return -1;
    }


    /**
     * 求解KMP的next数组
     *
     * @param substr
     * @return
     */
    private static int[] getNext(String substr) {
        /**
         * 如果知道next[j] = t,求next[j+1]
         * (1)如果Pj = Pt, 则next[j+1] = t + 1 = next[j] + 1
         * (2)如果 Pj != Pt,则循环设置 t = next[t],知道t = 0或者满足（1）为止， 当t = 0时， next[j + 1] = 1
         */

        int len = substr.length();
        //下标从1开始
        int[] next = new int[len + 1];
        //第1位不使用
        next[0] = -1;
        //下标为1也就是实际的第1位，规定是0，因为第一位不匹配，表示与主串的下一位进行比较
        next[1] = 0;

        int j = 1;
        int t = next[j];

        while (j < next.length - 1) {
            if (t == 0 || substr.charAt(j - 1) == substr.charAt(t - 1)) {
                t++;
                j++;
                next[j] = t;
            } else {
                t = next[t];
            }
        }
        return next;
    }


    /**
     * 求解KMP的next数组
     *
     * @param patterns
     * @return
     */
    private static int[] getNext(byte[] patterns) {
        /**
         * 如果知道next[j] = t,求next[j+1]
         * (1)如果Pj = Pt, 则next[j+1] = t + 1 = next[j] + 1
         * (2)如果 Pj != Pt,则循环设置 t = next[t],知道t = 0或者满足（1）为止， 当t = 0时， next[j + 1] = 1
         */

        int len = patterns.length;
        //下标从1开始
        int[] next = new int[len + 1];
        //第1位不使用
        next[0] = -1;
        //下标为1也就是实际的第1位，规定是0，因为第一位不匹配，表示与主串的下一位进行比较
        next[1] = 0;

        int j = 1;
        int t = next[j];

        while (j < next.length - 1) {
            if (t == 0 || patterns[j - 1] == patterns[t - 1]) {
                t++;
                j++;
                next[j] = t;
            } else {
                t = next[t];
            }
        }
        return next;
    }

    public static void main(String[] args) throws Exception {


        testGetNext();

    }


    private static void testByte() throws Exception {
        byte[] subBytes = new byte[]{7,2};
        byte[] bytes = new byte[]{7};
        int i = indexOfByte(bytes, subBytes);
        System.out.println(i);

    }

    private static void testGetNext() throws Exception {
        int [] next = getNext("ABABAAABABAA");
        System.out.println(Arrays.toString(next));
        System.out.println(Arrays.toString(getNext(new byte[]{8,2,8,3})));
    }



    private static void testStr1() throws Exception {
        int pos = indexOfStr("ABCDEFDMNEFXT", "T");
        System.out.println(pos);
    }

    private static void testStr() throws Exception {
        FileInputStream fileInputStream = new FileInputStream("D:\\gitSpace\\servlet_path\\src\\main\\resources\\sfz_zm_data");
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(fileInputStream.available());
        byteArrayOutputStream.write(fileInputStream);

        String s = new String(byteArrayOutputStream.toByteArray());
        s = "AAAAAAAAACAAAAAAAAACAAAAAAAAACAAAAAXAAAACAAAAAAAAACAAAAAAAAACAAAAAAAAACAAAAAAAAACAAAAAAAAAdAAAAAAAAACAAAAAAAAACAAAAAAAAACAAAAAAAAACAAAAAAAAACAAAAAAAAACAAAAAAAAACAAAAAAAAACds";
        String targetStr = "AAAAAAAAACds";
        char[] source = s.toCharArray();
        int sourceOffset = 0;
        int sourceCount = s.length();
        char[] target = targetStr.toCharArray();
        int targetOffset = 0;
        int targetCount = targetStr.length();
        int fromIndex = 0;
        //System.out.println(s);
        long start = System.currentTimeMillis();
        System.out.println("位置indexOf：" + s.indexOf(targetStr));
        long end = System.currentTimeMillis();
        System.out.println(end - start);

        start = System.currentTimeMillis();
        System.out.println("位置findStr：" + indexOfStr(s, targetStr));
        end = System.currentTimeMillis();
        System.out.println(end - start);
        /*int pos = findStr("ABCDEFDMNEFXT", "DEF");
        System.out.println(pos);
        System.out.println("ABCDEFDMNEFXT".indexOf("DEF"));*/
        //indexOf("ABCDEFDMNEFXT".toCharArray(), 0, "ABCDEFDMNEFXT".length(), "EFX".toCharArray(), 0, "EFX".length(), 0);
    }

}
