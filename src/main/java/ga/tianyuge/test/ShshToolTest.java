package ga.tianyuge.test;

import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.util.Base64;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

/**
 * @Description: description
 * @author: GuoqingChen
 * @Time: 2023/04/20 13:21
 * @Email: guoqing.chen01@hand-china.com
 */
public class ShshToolTest {
    /**
     * 压缩
     */
    public String zipString(String unzipString) {
        /**
         *     https://www.yiibai.com/javazip/javazip_deflater.html#article-start
         *     0 ~ 9 压缩等级 低到高
         *     public static final int BEST_COMPRESSION = 9;            最佳压缩的压缩级别。
         *     public static final int BEST_SPEED = 1;                  压缩级别最快的压缩。
         *     public static final int DEFAULT_COMPRESSION = -1;        默认压缩级别。
         *     public static final int DEFAULT_STRATEGY = 0;            默认压缩策略。
         *     public static final int DEFLATED = 8;                    压缩算法的压缩方法(目前唯一支持的压缩方法)。
         *     public static final int FILTERED = 1;                    压缩策略最适用于大部分数值较小且数据分布随机分布的数据。
         *     public static final int FULL_FLUSH = 3;                  压缩刷新模式，用于清除所有待处理的输出并重置拆卸器。
         *     public static final int HUFFMAN_ONLY = 2;                仅用于霍夫曼编码的压缩策略。
         *     public static final int NO_COMPRESSION = 0;              不压缩的压缩级别。
         *     public static final int NO_FLUSH = 0;                    用于实现最佳压缩结果的压缩刷新模式。
         *     public static final int SYNC_FLUSH = 2;                  用于清除所有未决输出的压缩刷新模式; 可能会降低某些压缩算法的压缩率。
         */

        //使用指定的压缩级别创建一个新的压缩器。
        Deflater deflater = new Deflater(Deflater.BEST_COMPRESSION);
        //设置压缩输入数据。
        deflater.setInput(unzipString.getBytes());
        //当被调用时，表示压缩应该以输入缓冲区的当前内容结束。
        deflater.finish();

        final byte[] bytes = new byte[256];
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(256);

        while (!deflater.finished()) {
            //压缩输入数据并用压缩数据填充指定的缓冲区。
            int length = deflater.deflate(bytes);
            outputStream.write(bytes, 0, length);
        }
        //关闭压缩器并丢弃任何未处理的输入。
        deflater.end();
        return Base64.getEncoder().encodeToString(outputStream.toByteArray());
    }

    /**
     * 解压缩
     */
    public String unzipString(String zipString) {
        byte[] decode = Base64.getDecoder().decode(zipString);

        //创建一个新的解压缩器  https://www.yiibai.com/javazip/javazip_inflater.html

        Inflater inflater = new Inflater();
        //设置解压缩的输入数据。
        inflater.setInput(decode);
        final byte[] bytes = new byte[256];
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(256);
        try {
            //finished() 如果已到达压缩数据流的末尾，则返回true。
            while (!inflater.finished()) {
                //将字节解压缩到指定的缓冲区中。
                int length = inflater.inflate(bytes);
                outputStream.write(bytes, 0, length);
            }
        } catch (DataFormatException e) {
            e.printStackTrace();
            return null;
        } finally {
            //关闭解压缩器并丢弃任何未处理的输入。
            inflater.end();
        }

        return outputStream.toString();
    }

    @Test
    public void unzipTest() {
        String str = "eNqNVF1r01AY/iuSazdOkn5g77rVC8F1RYcg4sVpeipx+SI5EcIYOKetumE7rHVz6tiYsovNTqxSO9Q/s6TJv/Cc0yRNugmGhJDnec/79bxvVjhoGIosQQ1zBc77cOT1N7irXNWuPUB4yTEQQUEKKEOVgsHB09GXt+5hl5GWJWvIssID1ysLtwHIEEbSVQNqzrxeo3hWAGACho68r3v+Rsvr9tzTTvDk82jvm7vZ9d6/CHba7rNjtzWgJ0wEMSqRhxwQgCDOAGGGz1/hswVRLPDiLHNrmybSpCjYrYU5AtaQAU2sIg3fqNEMgCBkActiwoSJuG8a7sv9YP2IkLJVQgrCqBaW/1Cvlm21isyxD5DLCQKBFVJ1aWnxpmyR7t1b4aAk6Xbs8HzQ9Nc6/vovb9gOdhvu7sFUWVCl1sSSJynNgviK+11CxoW8J8wleUvQkDFUIuEAn8bCE6Ozlt/+5HV6bnPI9NCwCSVc1gk3v1imMtEmk3gZICY6S+ny3QQQFToceqdb7mCNURbWVWTOQW05pg+Dx++C1z/8/U3/z8fz4ZY36LnPG+Rz+gDNQBTyAORoH3iaB3nlM0LC8H+7G9vrrBUZ6onPXmPSK/IjZDphm/gpKB7Mpnf8m3BINRTdQTEeLUmM6+mpqNtarWKbhm6FIrA7m2Yi8V7ted2dcRzKTpQbixdh0dY1m37/xO//HHXYmNLZKJbuJBQDWRpoebkYzRadGQM6dNAvLhAABXbPJqzCFIQ0Mln787NtQtmarGsV6MRrIQJeBHkmG918p17813Sv3ieeFaglfi/RZxSFzYp7sj1ujIUwVlAiN/ZvSaPRaHe+E90oW5fqoXMLh3Vzq38BNH/UBw==";
        System.out.println(str);
        String s = this.unzipString(str);
        System.out.println(s);
    }
}
