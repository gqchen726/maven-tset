package ga.tianyuge.test;

import jdk.nashorn.internal.runtime.regexp.joni.Regex;
import org.apache.commons.lang.StringEscapeUtils;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * description
 *
 * @author guoqing.chen01@hand-china.com 2022/09/13 14:03
 */
public class RegexTest {

    @Test
    public void test1() {
        String s = "五金材料（Ｐ7\'P 3）";
        System.out.println("before change : " + s);
        /*if (s.contains("\\")) {
            String s1 = s.replaceAll("\\\\", "\\\\\\\\\\\\\\\\");
//            String s1 = StringEscapeUtils.escapeSql(s);
            System.out.println("after change : " + s1);
        }*/
        String s1 = StringEscapeUtils.escapeSql(s);
        System.out.println("after change : " + s1);
    }

    @Test
    public void test2() {
        String s = "";
        List<String> stringList = new ArrayList<>();
        stringList.add("-23.1524");
        stringList.add("212e-5");
        stringList.add("1e-1");
        stringList.add("CON58002023004996");
        stringList.add("absafia(&^&UH");
        String regex = "-?\\d+(?:\\.\\d+)?(?:[eE][+-]?\\d+)?";
//        String SCIENTIFIC= "^([\\+-]?\ld+(.fo). \d+))[Ee]f11([\1+-]?\1d+)$";
        AtomicReference<Regex> regex1 = new AtomicReference<>();
        stringList.forEach(str -> {
            regex1.set(new Regex(s));
            System.out.println("text: " + str + ", result: " + regex.matches(regex));
        });
    }
}
