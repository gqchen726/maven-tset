import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * @Author guoqing.chen01@hand-china.com
 * @description
 * @Date Created in Administrator 2022/04/02 上午 11:46
 */
public class DateTest {

    public static void main(String[] args) throws ParseException {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        Calendar instance = Calendar.getInstance();
        instance.setTime(date);
        int i = instance.get(Calendar.WEEK_OF_MONTH);
        System.out.println("WEEK_OF_MONTH:"+i);

        Instant instant = date.toInstant();
        ZonedDateTime zonedDateTime = instant.atZone(ZoneId.systemDefault());
        int hour = zonedDateTime.getHour();
        int minute = zonedDateTime.getMinute();
        int second = zonedDateTime.getSecond();
        DayOfWeek dayOfWeek = zonedDateTime.getDayOfWeek();
        System.out.println("dayOfWeek:"+dayOfWeek);
        instance.add(Calendar.HOUR_OF_DAY, -1 * hour);
        instance.add(Calendar.MINUTE, -1 * minute);
        instance.add(Calendar.SECOND, -1 * second);
        int weekYear = instance.getWeekYear();
        System.out.println("weekYear:"+weekYear);

        Date time = instance.getTime();
        System.out.println(time);

        instance.add(Calendar.HOUR_OF_DAY, 23);
        instance.add(Calendar.MINUTE, 59);
        instance.add(Calendar.SECOND, 59);
        Date time1 = instance.getTime();
        System.out.println(time1);

        Calendar instance1 = Calendar.getInstance(TimeZone.getTimeZone(ZoneId.systemDefault()));
        instance1.setTime(date);
        instance1.setFirstDayOfWeek(Calendar.SUNDAY);
        System.out.println(instance1.getTime().toString());

        instance1.add(Calendar.WEEK_OF_MONTH, 1);

        LocalDate parse = LocalDate.parse(format.format(instance1.getTime()));
        LocalDate first = parse.with(DayOfWeek.SUNDAY);

        System.out.println(first.toString());

        String dateStr = "2020-11-01 00:00:00";
        Date dateDiy = format.parse(dateStr);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateDiy);
        calendar.add(Calendar.MONTH, 5);
        System.out.println(calendar.getTime().toString());
    }
}
