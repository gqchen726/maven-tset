package ga.tianyuge.test;

import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.temporal.IsoFields;
import java.time.temporal.WeekFields;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import java.util.TimeZone;

public class LocalDateTest {

    @Test
    public void localDateTset1() {
        LocalDate today = LocalDate.parse("2024-12-31");
        int weekOfYear = today.get(IsoFields.WEEK_OF_WEEK_BASED_YEAR);
        System.out.println("当前日期是今年的第 " + weekOfYear + " 周");
    }

    @Test
    public void localDateTset2() {
        String dateString = "2024-12-29";
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date;
        try {
            date = formatter.parse(dateString);
            System.out.println("解析的日期: " + date);
            Calendar calendar = Calendar.getInstance();
            // 设置时区为北京时区（中国标准时间）
            TimeZone timeZone = TimeZone.getTimeZone("Asia/Shanghai");
            calendar.setTimeZone(timeZone);
            calendar.setTime(Date.from(date.toInstant()));
            calendar.setFirstDayOfWeek(Calendar.MONDAY);
            int weekOfYear = calendar.get(Calendar.WEEK_OF_YEAR);
            System.out.println(dateString + " 日期是 " + calendar.getWeekYear() + " 年的第 " + weekOfYear + " 周");
        } catch (ParseException e) {
            System.out.println("日期解析失败: " + e.getMessage());
        }
    }

    @Test
    public void localDateTset3() {

        String dateString = "2024-12-30";
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date week = null;
        try {
            week = formatter.parse(dateString);
            System.out.println("解析的日期: " + week);
        } catch (ParseException e) {
            System.out.println("日期解析失败: " + e.getMessage());
        }
        if (Objects.nonNull(week)) {

            // 将字符串解析为 LocalDate 对象
            LocalDate date = LocalDate.parse(formatter.format(week));

            // 获取该日期的周数
            WeekFields weekFields = WeekFields.of(DayOfWeek.MONDAY,1);
            // 获取当前日期在年份中的第几周
            System.out.println(date.get(weekFields.weekOfYear()));
        }
    }
}
