package ga.tianyuge.test;

import org.junit.Test;

/**
 * description
 *
 * @author guoqing.chen01@hand-china.com 2022/09/14 9:34
 */
public class ExceptionTest {

    @Test
    public void byZeroExceptionTest() {
        System.out.println(0/1);
        System.out.println(1/0);
    }
}
