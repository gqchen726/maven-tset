package ga.tianyuge.utils;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.nio.charset.Charset;



import static org.bouncycastle.asn1.x500.style.RFC4519Style.cn;

/**
 * 转化txt文档的编码格式
 */
public class ConcertEncodeing {

    public static void main(String[] args) throws Exception {

//        convertCharset("D:\\workspaces\\workspaceOxygen\\ceshi",Charset.forName("GBK"),Charset.forName("UTF-8"),"java");
        File file = new File("C:/temp/test.txt");

        System.out.println(getFileCharset(file));
        File file1 = FileUtil.convertCharset(file, Charset.forName("UTF-8"), Charset.forName("GBK"));
        System.out.println(getFileCharset(file));
        System.out.println(getFileCharset(file1));

    }


    /**
     * 获取文件的编码格式
     *
     * @param sourceFile 源文件
     * @return 返回字符串形式的编码格式
     */
    private static String getFileCharset(File sourceFile) {
        String charset = "GBK";
        byte[] first3Bytes = new byte[3];
        try {
            boolean checked = false;
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(sourceFile));
            bis.mark(0);
            int read = bis.read(first3Bytes, 0, 3);
            if (read == -1) {
                // 文件编码为 ANSI
                return charset;
            } else if (first3Bytes[0] == (byte) 0xFF && first3Bytes[1] == (byte) 0xFE) {
                // 文件编码为 Unicode
                charset = "UTF-16LE";
                checked = true;
            } else if (first3Bytes[0] == (byte) 0xFE && first3Bytes[1] == (byte) 0xFF) {
                // 文件编码为 Unicode big endian
                charset = "UTF-16BE";
                checked = true;
            } else if (first3Bytes[0] == (byte) 0xEF && first3Bytes[1] == (byte) 0xBB
                    && first3Bytes[2] == (byte) 0xBF) {
                // 文件编码为 UTF-8
                charset = "UTF-8";
                checked = true;
            }
            bis.reset();
            if (!checked) {
                int loc = 0;
                while ((read = bis.read()) != -1) {
                    loc++;
                    if (read >= 0xF0) {
                        break;
                    }
                    // 单独出现BF以下的，也算是GBK
                    if (0x80 <= read && read <= 0xBF)
                    {
                        break;
                    }
                    if (0xC0 <= read && read <= 0xDF) {
                        read = bis.read();
                        // 双字节 (0xC0 - 0xDF)
                        if (0x80 <= read && read <= 0xBF)
                            // (0x80
                            // - 0xBF),也可能在GB编码内
                        {
                            continue;
                        } else {
                            break;
                        }
                    // 也有可能出错，但是几率较小
                    } else if (0xE0 <= read && read <= 0xEF) {
                        read = bis.read();
                        if (0x80 <= read && read <= 0xBF) {
                            read = bis.read();
                            if (0x80 <= read && read <= 0xBF) {
                                charset = "UTF-8";
                                break;
                            } else {
                                break;
                            }
                        } else {
                            break;
                        }
                    }
                }
            }
            bis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return charset;
    }
}
