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
}
