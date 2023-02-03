package ga.tianyuge.test;

import com.alibaba.fastjson.JSONObject;
import ga.tianyuge.bean.Employee;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * description
 *
 * @author guoqing.chen01@hand-china.com 2022/10/19 15:45
 */
public class ListTest {
    private static Integer UPDATE_THRESHOLD_VALUE = 2000;

//    @Test
    public void subListTest(List<Integer> integerList) {
        /*long newI = 0;
        for (long i = 0; i < 20001;) {
            newI = i + UPDATE_THRESHOLD_VALUE;
            System.out.println("i: " + i + ", newI: " + newI);
            i = newI;
        }
        System.out.println(8/1000);*/
        int newI = 0;
        for (int i = 0; i < integerList.size(); i++) {
            newI = i + UPDATE_THRESHOLD_VALUE;
            System.out.println("i: " + i + ", newI: " + newI);
            newI = Math.min(newI, integerList.size());
            System.out.println(integerList.subList(i, newI));
            i = newI;
        }
    }

    @Test
    public void listPrintTest() {
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee("1", "1"));
        employees.add(new Employee("2", "2"));
        employees.add(new Employee("3", "3"));

        employees.stream()
                .map(JSONObject::toJSONString)
                .forEach(System.out::println);
    }

    @Test
    public void subListExceptionTest() {
        List<Integer> integerList = new ArrayList<>();
        for (int j = 1; j < 65536; j++) {
            integerList.add(j);
        }
        subListTest(integerList);
    }

    /**
     * 拷贝测试
     */
    @Test
    public void copyTest() {
        List<String> a = new ArrayList<>();

        a.add("a");
        a.add("1");
        a.add("!");

        List<String> b = new ArrayList<>(a);

        a.add("b");

        a.forEach(System.out::print);
        System.out.println();
        b.forEach(System.out::print);

    }
}
