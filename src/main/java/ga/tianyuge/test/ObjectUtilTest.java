package ga.tianyuge.test;

import ga.tianyuge.bean.Employee;
import org.apache.commons.lang3.ObjectUtils;
import org.junit.Test;

/**
 * description
 *
 * @author guoqing.chen01@hand-china.com 2022/10/19 15:05
 */
public class ObjectUtilTest {

    @Test
    public void test1() {
        Employee employee = new Employee();
        System.out.println(ObjectUtils.allNotNull(employee));
        System.out.println(ObjectUtils.notEqual(employee, null));
    }
}
