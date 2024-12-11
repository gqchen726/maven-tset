package ga.tianyuge.test;

import org.junit.Test;

/**
 * description
 *
 * @author guoqing.chen01@hand-china.com 2022/09/13 9:41
 */
public class NullPointExceptionTest {

    @Test
    public void switchTest() {
        String s = null;
        switch (s) {
            case "1" :
                System.out.println("1");
        }
    }

    @Test
    public void equalsTest() {
        Integer a = null;
//        System.out.println(a == 0);
        try {
//            System.out.println(0 == a);
            System.out.println(a == 0);

        } catch (Exception e) {
//            System.out.println(e.getCause().toString());
            e.printStackTrace();
        }
//        System.out.println();
    }

    @Test
    public void euqualsNullText() {
        Long a = null;
        System.out.println(a == 1);
    }
}
