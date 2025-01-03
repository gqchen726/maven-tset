package ga.tianyuge.test;

import com.alibaba.fastjson.JSON;
import ga.tianyuge.bean.Employee;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Iterator;
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

    @Test
    public void test() {
        List<Long> a1 = new ArrayList<>();
        a1.add(1L);
        a1.add(2L);
        a1.add(3L);

        Iterator<Long> iterator = a1.iterator();
        while (iterator.hasNext()) {
            Long next = iterator.next();
            if (next == 2L) iterator.remove();
            System.out.println(next);
            System.out.println(a1);
        }
    }

    @Test
    public void foreachTest() {
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee("a1"));
        employees.add(new Employee("b2"));
        employees.add(new Employee("c3"));
        System.out.println(JSON.toJSONString(employees));
        for (Employee employee : employees) {
            employee.setName("TEST");
        }
        System.out.println(JSON.toJSONString(employees));
    }
}
