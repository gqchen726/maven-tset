package ga.tianyuge.test;

import org.junit.Test;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Description: description
 * @author: GuoqingChen
 * @Time: 2023/02/03 14:06
 * @Email: guoqing.chen01@hand-china.com
 */
public class AtomicIntegerTest {

    @Test
    public void test() {
        AtomicInteger index = new AtomicInteger();
        System.out.println(index.get());
        System.out.println(index.incrementAndGet());
    }
}
