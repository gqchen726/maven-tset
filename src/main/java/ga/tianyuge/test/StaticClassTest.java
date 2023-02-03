package ga.tianyuge.test;

import ga.tianyuge.bean.StaticClass;
import org.junit.Test;

/**
 * description
 *
 * @author guoqing.chen01@hand-china.com 2022/11/24 11:23
 */
public class StaticClassTest {

    @Test
    public void test() throws InterruptedException {
        Thread.sleep(2000);
        StaticClass.A();
//        StaticClass.B();
    }
}
