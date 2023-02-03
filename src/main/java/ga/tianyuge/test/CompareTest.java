package ga.tianyuge.test;

import org.junit.Test;

import java.math.BigDecimal;

/**
 * description
 *
 * @author guoqing.chen01@hand-china.com 2022/11/22 13:59
 */
public class CompareTest {
    @Test
    public void BigDecimalAndNullTest() {
        System.out.println(BigDecimal.ZERO.compareTo(new BigDecimal(-1)));
    }
}
