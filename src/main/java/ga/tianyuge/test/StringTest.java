package ga.tianyuge.test;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

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
    public void test1() {
        System.out.println("NEW".hashCode());
    }

    @Test
    public void test() {

        String str = "134";
        String str1 = "00000" + str;
        String s = str1.substring(str.length());
        System.out.println(s);
    }

    @Test
    public void test2() {
        String[] split = "".split(",");
        List<String> strings = Arrays.asList(split);
        for (String s : strings) {
            System.out.println(s);
        }
    }
}
