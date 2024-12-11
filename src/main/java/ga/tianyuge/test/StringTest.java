package ga.tianyuge.test;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
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

    @Test
    public void containTest() throws IOException {
        String str = "STATUS\":\"F";
        FileIOTest fileIOTest = new FileIOTest();
        String s = fileIOTest.readByFile();
        System.out.println(s);
        System.out.println(str);
        System.out.println(s.contains(str));
    }

    @Test
    public void dealWithXml() throws IOException {
        FileIOTest fileIOTest = new FileIOTest();
        String s = fileIOTest.readByFile();
        String s1 = StringEscapeUtils.escapeXml(s);
    }

    @Test
    public void substringTest() throws Exception {
        String fileUrl = "http://scptest.shenghongpec.com:9000/dev-private-bucket/hsdr01/3/d6366f88e17c4ae7ad8038fb2876953e@test.docx";
        System.out.println(fileUrl.substring(fileUrl.lastIndexOf("/") + 1));
        URL url = new URL(fileUrl);
        InputStream inputStream = url.openStream();
        OutputStream outputStream = new ByteArrayOutputStream();
        IOUtils.copy(url.openStream(), outputStream);
    }

    @Test
    public void splitTest1() {
        String str = "http://shsrm.shenghongpec.com:39000/srm-prod-private-bucket/spfm-comp/0/412e5a12c16948c38945d3a685a061e6@巨榭营业执照（副本）2021年.pdf";
        System.out.println(str.split("/", 5)[4]);
    }

    @Test
    public void nullTestToStringTest() {
        System.out.println("".toString());
    }

    @Test
    public void subStringTest() {
        String levelPath = "88";
        System.out.println(levelPath.substring(levelPath.indexOf("|") + 1));
        if (levelPath.indexOf("|") != levelPath.lastIndexOf("|")) {
            System.out.println(levelPath.substring(0, levelPath.indexOf("|")));
        }
    }

    @Test
    public void containsTest() throws URISyntaxException {
        String api = "http://1.1.1.1/iam/hzero/users/self";
        URI uri = new URI(api);
        String whileApi = "/iam/hzero/users/self";
        System.out.println(uri.toString().contains(whileApi));
    }

    @Test
    public void vaguePhoneTest() {
//        String data = "18907894672";
        String data = "142427199810071980";
//        System.out.println(String.format("%s****%s", data.substring(0, 3), data.substring(7, 11)));
        System.out.println(String.format("%s****%s", data.substring(0, 3), data.substring(14, 18)));
    }
}
