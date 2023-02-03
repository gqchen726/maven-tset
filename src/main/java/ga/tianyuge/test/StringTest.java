package ga.tianyuge.test;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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

    @Test
    public void test3() {
        String a = "1827ha|w73r|f";
        System.out.println(a.contains("|"));
        System.out.println(a.contains("\\|"));
        String[] split = a.split("|");
        String[] split1 = a.split("\\|");
        Arrays.stream(split).forEach(System.out::println);
        Arrays.stream(split1).forEach(System.out::println);
    }

    @Test
    public void replaceAllTest() {
        List<String> replaceTarget = new ArrayList<>();
        replaceTarget.add(",");
        replaceTarget.add("，");
        String str = "11006750，11006751,11006752，11006753,11006754,11006755,11006796,11006797,11006798，52309311,52309312,52309313,52309314，11006738，11006739，11006795，52309310,52309393";

        for (String s : replaceTarget) {
            str = str.replaceAll(s, ",");
        }
        System.out.println(str);
    }

    public String replaceSpecialChar(String str) {
        return str.replaceAll(" ", "").replaceAll("\u0002", "");
    }

    @Test
    public void splitTest() {
        String text = "http://shsrm.shenghongpec.com:39000/srm-prod-private-bucket/spcm-supplier/3/389c0ec4c15b4f508b4e1cb0f7c8f436@CON53002023001519-2023-2025年度虹港硫酸-斯尔邦-无价合同.pdf";
        if (text.contains("/")) {
            String[] split = text.split("/");
//            List<String> strings = Arrays.asList(split);
//            strings = strings.stream().filter(StringUtils::isNoneBlank).collect(Collectors.toList());
            for (String s : split) {
                System.out.println(s);
            }
        }
    }


}
