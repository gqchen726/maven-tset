package ga.tianyuge.test;

import ga.tianyuge.bean.Employee;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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

    @Test
    public void stringNumberCompareTest() {
        List<Employee> list = new ArrayList<>();
//        list.add(new Employee("1", "a"));
//        list.add(new Employee("2", "a"));
//        list.add(new Employee("3", "a"));
//        list.add(new Employee("4", "a"));
        list.add(new Employee("10", "b"));
        list.add(new Employee("10", "a"));
        list.add(new Employee("11", "a"));
        list.add(new Employee("21", "c"));
//        list.add(new Employee("55", "ccc"));
        System.out.println(list);
        list = list.stream()
                .sorted(Comparator.comparing(Employee::getName))
                .sorted(Comparator.comparing(t -> Long.parseLong(t.getEmployeeCode())))
                .collect(Collectors.toList());
        System.out.println(list);
    }
}
