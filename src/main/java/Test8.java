import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * @author: guoqing.chen01@hand-china.com 2022-03-03 15:41
 **/

public class Test8 {
    public static void main(String[] args) throws ParseException {
        /*Date date = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(date);


        SimpleDateFormat sdfMonth = new SimpleDateFormat("yyyyMM");
        String MONTH = sdfMonth.format(date);
        System.out.println(MONTH);
        c.add(Calendar.MONTH, -1);
        String LAST_MONTH = sdfMonth.format(c.getTime());


        System.out.println("20220225".trim().startsWith(MONTH));*/

        /*String str = "env.txt";
        System.out.println(str.contains("."));
        String[] split = str.split("\\.");
        for (String s : split) {
            System.out.println(s);
        }*/

/*//        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = format.parse("2022-03-13");

        Instant instant = date.toInstant();
        ZonedDateTime zonedDateTime = instant.atZone(ZoneId.systemDefault());
        DayOfWeek dayOfWeek = zonedDateTime.getDayOfWeek();

        System.out.println(dayOfWeek);*/

        String jsonResult = null;
        if (StringUtils.isBlank(jsonResult)) {

            Date date = new Date();
            Calendar c = Calendar.getInstance(TimeZone.getDefault());
            c.setTime(date);
            c.add(Calendar.DAY_OF_MONTH, 1);

            Date time = c.getTime();
            Instant instant = time.toInstant();
            ZonedDateTime zonedDateTime = instant.atZone(ZoneId.systemDefault());
            DayOfWeek dayOfWeek = zonedDateTime.getDayOfWeek();
            if (DayOfWeek.SATURDAY.equals(dayOfWeek) || DayOfWeek.SUNDAY.equals(dayOfWeek)) {
                System.out.println("xxx");
            }
        }
    }
}
