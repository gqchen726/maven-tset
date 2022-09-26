package ga.tianyuge.test;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * description
 *
 * @author guoqing.chen01@hand-china.com 2022/07/28 14:23
 */
public class CollectionTest {

    @Test
    public void ListTest() {
        List<Long> a1 = new ArrayList<>();
        a1.add(1L);
        a1.add(2L);
        a1.add(3L);

        List<Long> a2 = new ArrayList<>();
        a2.add(2L);
        a2.add(3L);
        a2.add(4L);

        a1.retainAll(a2);
        System.out.println(a1);
        System.out.println(a2);
    }
}
