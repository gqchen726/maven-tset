package ga.tianyuge.test;

import ga.tianyuge.bean.Employee;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

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

    @Test
    public void reduceTest1() {
        List<Employee> result = new ArrayList<>();
//        result.add(new Employee("1", "1", new HashMap<>()));
        /*Map<String, String> collect =
                result.stream()
                        .collect(Collectors.toMap(Employee::getApprovalStatus, Employee::getApproverCode, (k1, k2) -> k2));*/
        /*List<Employee> collect =
                result.stream()
                        .peek(employee -> employee.setEmployeeId(Long.valueOf(employee.getFlex().get("employeeId"))))
                        .filter(t -> Objects.nonNull(t.getEmployeeId()))
                        .collect(Collectors.toList());
        System.out.println(collect);*/
        result.stream().collect(Collectors.toMap(Employee::getName, Employee::getEmployeeCode, (k1, k2) -> k2));

    }
}
