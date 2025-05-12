package ga.tianyuge.test;

import org.junit.Test;

/**
 * description
 *
 * @author guoqing.chen01@hand-china.com 2022/07/28 15:23
 */
public class IntTest {

    @Test
    public void test() {
        String a = "    5";
        a = a.trim();
        System.out.println(Integer.parseInt(a));
    }

    @Test
    public void test1() {
        int index = 0;
        System.out.println(index);
        System.out.println(index+=3);
        System.out.println(index);
    }
}
