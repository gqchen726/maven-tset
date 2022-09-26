package ga.tianyuge.test;

import org.apache.commons.lang.StringEscapeUtils;
import org.junit.Test;

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
}
