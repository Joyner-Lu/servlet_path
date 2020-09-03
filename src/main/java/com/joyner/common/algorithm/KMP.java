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

    private static long my_count = 0;
    private static long they_count = 0;

    /**
     * 查询是否subStr存在于str中，如果存在，则返回匹配的第一个index的值
     *
     * @param str
     * @param subStr
     * @return 返回-1表示未查询掉
     */
    public static int findStr(String str, String subStr) {
        //char[] chars = str.toCharArray();
        //char[] subChars = subStr.toCharArray();
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

            my_count++;
            char c = str.charAt(j);
            if (i == 0) {
                char firstChar = subStr.charAt(0);
                if (c != firstChar) {
                    continue;
                }
                //取下一个字符
                j++;
                i++;
                c = str.charAt(j);
            }

            //比较剩下的位
            for (; i < subLen; i++) {

                my_count++;
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
     * 求解KMP的next数组
     *
     * @param substr
     * @return
     */
    private static int[] getNext(String substr) {
        int len = substr.length();
        int[] next = new int[len + 1];
        // 这个i永远只会往前走，而j会随着不匹配而逐渐等于next[j]
        int j = 1, t = 0;
        next[0] = -1;
        next[1] = 0;//第一个比较特殊，直接规定为0
        while (j < len) {
            // t=0 表示当前指向的是第一个元素
            if (t == 0 || substr.charAt(j) == substr.charAt(t)) {
                // 前面提到过，前面的匹配，所以让后一个等于前一个的下标+1
                ++j;
                ++t;
                next[j] = t;
            } else {
                t = next[t];
            }
        }
        return next;
    }

    static int indexOf(char[] source, int sourceOffset, int sourceCount,
                       char[] target, int targetOffset, int targetCount,
                       int fromIndex) {
        if (fromIndex >= sourceCount) {
            return (targetCount == 0 ? sourceCount : -1);
        }
        if (fromIndex < 0) {
            fromIndex = 0;
        }
        if (targetCount == 0) {
            return fromIndex;
        }

        char first = target[targetOffset];
        int max = sourceOffset + (sourceCount - targetCount);

        for (int i = sourceOffset + fromIndex; i <= max; i++) {
            they_count++;
            /* Look for first character. */
            if (source[i] != first) {
                while (++i <= max && source[i] != first && they_count++ > 0);
            }

            /* Found first character, now look at the rest of v2 */
            if (i <= max) {
                int j = i + 1;
                int end = j + targetCount - 1;
                for (int k = targetOffset + 1; j < end && source[j]
                        == target[k] && they_count++ > 0; j++, k++);

                if (j == end) {
                    /* Found whole string. */
                    return i - sourceOffset;
                }
            }
        }
        return -1;
    }


    public static void main(String[] args) throws Exception {
        FileInputStream fileInputStream = new FileInputStream("D:\\gitSpace\\servlet_path\\src\\main\\resources\\sfz_zm_data");
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(fileInputStream.available());
        byteArrayOutputStream.write(fileInputStream);

        String s = new String(byteArrayOutputStream.toByteArray());
        //s = "AAAAAAAAACAAAAAAAAACAAAAAAAAACAAAAAAAAACAAAAAAAAACAAAAAAAAACAAAAAAAAACAAAAAAAAACAAAAAAAAACAAAAAAAAACAAAAAAAAACAAAAAAAAACAAAAAAAAACAAAAAAAAACAAAAAAAAACAAAAAAAAACAAAAAAAAAC";
        String targetStr = "keMO";
        char[] source = s.toCharArray();
        int sourceOffset = 0;
        int sourceCount = s.length();
        char[] target = targetStr.toCharArray();
        int targetOffset = 0;
        int targetCount = targetStr.length();
        int fromIndex = 0;
        //System.out.println(s);
        long start = System.currentTimeMillis();
        System.out.println("位置indexOf：" + indexOf(source, sourceOffset, sourceCount,
        target, targetOffset, targetCount,
        fromIndex));
        long end = System.currentTimeMillis();
        System.out.println(end - start);

        start = System.currentTimeMillis();
        System.out.println("位置findStr：" + findStr(s, targetStr));
        end = System.currentTimeMillis();
        System.out.println(end - start);
        /*int pos = findStr("ABCDEFDMNEFXT", "DEF");
        System.out.println(pos);
        System.out.println("ABCDEFDMNEFXT".indexOf("DEF"));*/
        //indexOf("ABCDEFDMNEFXT".toCharArray(), 0, "ABCDEFDMNEFXT".length(), "EFX".toCharArray(), 0, "EFX".length(), 0);
        System.out.println("my_count:" + my_count);
        System.out.println("they_count:" + they_count);
    }

}
