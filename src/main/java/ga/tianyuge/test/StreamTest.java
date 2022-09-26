package ga.tianyuge.test;

import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * description
 *
 * @author guoqing.chen01@hand-china.com 2022/08/12 15:56
 */
public class StreamTest {

    @Test
    public void reduceTest() {
        List<BigDecimal> bigDecimalList = new ArrayList<>();
        bigDecimalList.add(new BigDecimal("3900"));
        BigDecimal bigDecimal = new BigDecimal("58430.68");

        BigDecimal reduce = bigDecimalList.stream().reduce(BigDecimal.ZERO, BigDecimal::add);
        System.out.println(bigDecimal.compareTo(reduce.add(bigDecimal).add(BigDecimal.ZERO)));

    }
}
