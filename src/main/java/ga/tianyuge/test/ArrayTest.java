package ga.tianyuge.test;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * description
 *
 * @author guoqing.chen01@hand-china.com 2022/12/12 16:34
 */
public class ArrayTest {

    @Test
    public void test1() {
        String[] companyList = { "913207000676274307", "913207005668923863", "9132070057037483XG", "9132070059004095XG", "91320700585543210A", "913207033983311165", "91320703MA1MEFWW1N", "913207030695410245" };
        String[] companyList1 = {"913207000676274308"};
        String[] companyList2;
        List<String> companyListTemp1 = new ArrayList<>(Arrays.asList(companyList));
        List<String> companyListTemp2 = new ArrayList<>(Arrays.asList(companyList1));
        companyListTemp2 = companyListTemp2.stream()
                .filter(StringUtils::isNoneBlank)
                .collect(Collectors.toList());
        companyListTemp1.removeAll(companyListTemp2);
        companyListTemp1.addAll(companyListTemp2);
        companyList = companyListTemp1.toArray(companyList);
        for (String s : companyList) {
            System.out.println(s);
        }

    }
}
