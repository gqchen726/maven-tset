
import java.util.Arrays;

/**
 * @author: guoqing.chen01@hand-china.com 2022-02-14 14:50
 **/

public class Test7 {

    public static void main(String[] args) {
        /*String s = "法国娇兰小黑裙淡香水水水水水水水水水水水水 G01345200";
        byte[] bytes = s.getBytes();
        System.out.println(s);
        System.out.println(bytes.length);
        if (bytes.length > 30) {
            byte[] bytes1 = Arrays.copyOf(bytes, 30);
            String s1 = new String(bytes1);
            System.out.println(s1);
            System.out.println(s1.getBytes().length);
        }*/

        System.out.println("1".length());
        System.out.println("😂".length());

        String B = "𝄞"; // 这个就是那个音符字符，只不过由于当前的网页没支持这种编码，所以没显示。
        String C = "\uD834\uDD1E";// 这个就是音符字符的UTF-16编码

        System.out.println(C);
        System.out.println(B.length());
        System.out.println(B.codePointCount(0,B.length()));
        // 想获取这个Java文件自己进行演示的，可以在我的公众号【程序员乔戈里】后台回复 6666 获取
        String b ="𝄞";
        System.out.println(b.codePointCount(0,b.length()));
    }


}
