package ga.tianyuge.test;

import org.apache.commons.lang3.ObjectUtils;
import org.junit.Test;

/**
 * description
 *
 * @author guoqing.chen01@hand-china.com 2022/09/09 10:32
 */
public class ObjectTest {

    @Test
    public void test1() {
        Object obj1 = "";
        Object obj2 = null;
        if ("".equals(obj1)) {
            obj1 = null;
        }
        if ("".equals(obj2)) {
            obj2 = null;
        }
        System.out.println(ObjectUtils.notEqual(obj1, obj2));
    }
}
