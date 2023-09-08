package ga.tianyuge.utils;

import org.junit.Test;

import java.util.Calendar;

/**
 * @Description: description
 * @author: GuoqingChen
 * @Time: 2023/06/13 16:49
 * @Email: guoqing.chen01@hand-china.com
 */
public class CalendarTest {

    @Test
    public void getInstanceTest() {
        System.out.println(Calendar.getInstance().get(Calendar.YEAR));
    }
}