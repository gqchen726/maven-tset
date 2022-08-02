import org.junit.Test;

/**
 * @Author guoqing.chen01@hand-china.com
 * @description
 * @Date Created in Administrator 2022/04/18 16:42
 */
public class StringTest {
    public static void main(String[] args) {

        String str = "000001384";
        String s = str.replaceFirst("^0*", "");
        System.out.println(s);
    }

    @Test
    public void test() {

        String str = "134";
        String str1 = "00000" + str;
        String s = str1.substring(str.length());
        System.out.println(s);
    }
}
