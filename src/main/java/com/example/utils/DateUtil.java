package com.example.utils;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author caohaifeng02100
 * @version v1.0
 * @Title
 * @Package
 * @Description:
 * @date 2020/8/19
 */
public class DateUtil {

    /**
     * Instant，表示的是时间戳，用于记录某一时刻的更改（JDK8之前的Timestamp）;
     * ZoneId 时区标志，比如用于标志欧洲/巴黎；
     * ZoneOffset 时区偏移量，与UTC的偏移量；
     * ZonedDateTime 与时区有关的日历系统，比如2007-12 03t10:15:30+01欧洲/巴黎；
     * OffsetDateTime 用于与UTC偏移的日期时间，如如2007-12 03t10:15:30+01:00。
     */

    private static DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static DateTimeFormatter DATETIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static DateTimeFormatter DATETIME_SSS_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

    private static DateTimeFormatter DATETIME_FORMATTER_HH = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:00:00");


    /**
     * 字符串转对象
     * yyyy-MM-dd HH:mm:ss -> Date
     *
     * @param dateStr
     * @return
     */
    public static Date strToDate(String dateStr) {
        LocalDateTime parse = LocalDateTime.parse(dateStr, DATETIME_FORMATTER);
        return localDateTimeToDate(parse);
    }

    /**
     * 判断两日期对象是否同一天
     * 仅判断年月日（包含起止日期）
     *
     * @param compareDate
     * @param from
     * @param to
     * @return
     */
    public static boolean isDateBetween(Date compareDate, Date from, Date to) {
        LocalDate compare = dateToLocalDate(compareDate);
        LocalDate fromDate = dateToLocalDate(from);
        LocalDate toDate = dateToLocalDate(to);
        boolean first = compare.compareTo(fromDate) >= 0;
        boolean second = compare.compareTo(toDate) <= 0;
        return first && second;
    }

    /**
     * 判断是否同一时刻，精确到ms
     * 可判断年月日时分秒
     *
     * @param compareDate
     * @param from
     * @param to
     * @return
     */
    public static boolean isBetween(Date compareDate, Date from, Date to) {
        boolean first = compareDate.getTime() - from.getTime() >= 0;
        boolean second = compareDate.getTime() - to.getTime() <= 0;
        return first && second;
    }

    /**
     * 日期对象转字符串
     *
     * @return yyyy-MM-dd
     */
    public static String toLocalDateStr(Date date) {
        LocalDate localDate = dateToLocalDate(date);
        return DATE_FORMAT.format(localDate);
    }

    public static String toLocalDateStr(LocalDate localDate) {
        return DATE_FORMAT.format(localDate);
    }


    /**
     * 日期对象转字符串
     * 默认format格式
     *
     * @param date
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String toLocalDateTimeStr(Date date) {
        LocalDateTime localDateTime = dateToLocalDateTime(date);
        return DATETIME_FORMATTER.format(localDateTime);
    }

    public static String toLocalDateTimeStr(LocalDateTime localDateTime) {
        return DATETIME_FORMATTER.format(localDateTime);
    }

    /**
     * 指定format格式
     *
     * @return 指定format格式
     */
    public static String toLocalDateTimeStr(Date date, DateTimeFormatter dateTimeFormatter) {
        LocalDateTime localDateTime = dateToLocalDateTime(date);
        return dateTimeFormatter.format(localDateTime);
    }


    /**
     * 返回精确到小时的日期对象
     *
     * @param date
     * @return yyyy-MM-dd HH:00:00
     */
    public static Date toDateTimeHH(Date date) {
        LocalDateTime localDateTime = dateToLocalDateTime(date);
        LocalDateTime of = LocalDateTime.of(localDateTime.getYear(), localDateTime.getMonth()
                , localDateTime.getDayOfMonth(), localDateTime.getHour(), 0);
        return Date.from(of.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * 获取某天开始时间
     *
     * @param date
     * @return yyyy-MM-dd 00:00:00
     */
    public static Date toDateTimeFirst(Date date) {
        LocalDateTime localDateTime = dateToLocalDateTime(date);
        LocalDateTime of = LocalDateTime.of(localDateTime.getYear(), localDateTime.getMonth()
                , localDateTime.getDayOfMonth(), 0, 0, 0);
        return Date.from(of.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * 获取某天结束时间
     *
     * @param date
     * @return yyyy-MM-dd 23:59:59
     */
    public static Date toDateTimeEnd(Date date) {
        LocalDateTime localDateTime = dateToLocalDateTime(date);
        LocalDateTime of = LocalDateTime.of(localDateTime.getYear(), localDateTime.getMonth()
                , localDateTime.getDayOfMonth(), 23, 59, 59);
        return Date.from(of.atZone(ZoneId.systemDefault()).toInstant());
    }


    /**
     * 获取给定日期所在月份的第一天日期1
     *
     * @return 月份第一天
     */
    public static Date getFirstDate(Date date) {
        LocalDate localDate = dateToLocalDate(date);
        LocalDate of = LocalDate.of(localDate.getYear(), localDate.getMonth(), 1);
        return Date.from(of.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    /**
     * 获取指定日期所在月份第一天日期2
     *
     * @return 月份第一天
     */
    public static Date getFirstDayOfMonth(Date date) {
        LocalDate localDate = dateToLocalDate(date);
        LocalDate firstDayOfMonth = localDate.with(TemporalAdjusters.firstDayOfMonth());
        return localDateToDate(firstDayOfMonth);
    }

    /**
     * 获取指定日期所在月份最后一天日期
     *
     * @return 月份最后一天
     */
    public static Date getLastDayOfMonth(Date date) {
        LocalDate localDate = dateToLocalDate(date);
        LocalDate lastDayOfMonth = localDate.with(TemporalAdjusters.lastDayOfMonth());
        return localDateToDate(lastDayOfMonth);
    }

    /**
     * 获取指定日期往后n天
     *
     * @param date
     * @return 往后n天
     */
    public static Date getPlusDays(Date date, Long n) {
        LocalDate localDate = dateToLocalDate(date);
        LocalDate plused = localDate.plusDays(n);
        return localDateToDate(plused);
    }

    /**
     * 获取指定日期往前n天
     *
     * @param date
     * @return 往前n天
     */
    public static Date getMinusDays(Date date, Long n) {
        LocalDate localDate = dateToLocalDate(date);
        LocalDate minused = localDate.minusDays(n);
        return localDateToDate(minused);
    }

    /**
     * 获取给定日期所在月份的最后一天日期
     *
     * @return 月份最后一天最后一秒
     */
    public static Date getLastDate(Date date) {
        LocalDate localDate = dateToLocalDate(date);
        ZonedDateTime zonedDateTime = LocalDateTime.of(localDate.getYear(), localDate.getMonth(),
                localDate.lengthOfMonth(), 23, 59, 59)
                .atZone(ZoneId.systemDefault());
        return Date.from(zonedDateTime.toInstant());
    }


    /**
     * 获取当天最后一秒
     *
     * @return yyyy:MM:dd 23:59:59
     */
    public static LocalDateTime getLastSecondOfDay(LocalDate value) {
        return value.atStartOfDay().plusDays(1).minusSeconds(1);
    }

    /**
     * 1若给定日期为某天00:00:00, 则将其调整为当天23:59:59(注意: 将改变原有对象)
     */
    public static void setLastSecondOfDay(Date value) {
        Objects.requireNonNull(value, "日期参数不能为空");
        LocalDateTime param = LocalDateTime.ofInstant(value.toInstant(), ZoneId.systemDefault());
        //判断时间参数为00:00:00
        if (param.getHour() == 0 && param.getMinute() == 0 && param.getSecond() == 0) {
            //将日期修改为当天的23:59:59
            value.setTime(value.toInstant().plusSeconds(86399).toEpochMilli());
        }
    }

    /**
     * 2若给定日期为某天00:00:00, 则将其调整为当天23:59:59(注意: 将改变原有对象)
     */
    public static Date getLastSecondDate(Date value) {
        return localDateTimeToDate(getLastSecondLDT(value));
    }

    /**
     * 若给定日期为某天00:00:00, 则将其调整为当天23:59:59(注意: 将改变原有对象)
     */
    public static LocalDateTime getLastSecondLDT(Date value) {
        Objects.requireNonNull(value, "日期参数不能为空");
        LocalDate valueDate = dateToLocalDate(value);
        LocalDateTime param = valueDate.atStartOfDay();
        return param.plusSeconds(86399);
    }

    /**
     * todo
     * 获取两日期段内所有日期list
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public static List<String> getListBeetwenDate(Date startDate, Date endDate) {
        return null;
    }

    /**
     * 获取两日期相隔天数
     * startDate：2020-08-18 endDate：2020-08-19 返回 1
     * startDate：2020-08-19 endDate：2020-08-18 返回 -1
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public Long getDaysBeweenDate(Date startDate, Date endDate) {
        return ChronoUnit.DAYS.between(dateToLocalDate(startDate), dateToLocalDate(endDate));
    }

    public Long getDaysBetween(Date startDate, Date endDate) {
        LocalDate localStartDate = dateToLocalDate(startDate);
        LocalDate localEndDate = dateToLocalDate(endDate);
        return localStartDate.until(localEndDate, ChronoUnit.DAYS);
    }

    public static void ChronoUnit() {
        LocalDateTime oldDate = LocalDateTime.of(2017, Month.AUGUST, 31, 10, 20, 55);
        LocalDateTime newDate = LocalDateTime.of(2018, Month.NOVEMBER, 9, 10, 21, 56);

        System.out.println(oldDate);
        System.out.println(newDate);

        // count between dates
        long years = ChronoUnit.YEARS.between(oldDate, newDate);
        long months = ChronoUnit.MONTHS.between(oldDate, newDate);
        long weeks = ChronoUnit.WEEKS.between(oldDate, newDate);
        long days = ChronoUnit.DAYS.between(oldDate, newDate);
        long hours = ChronoUnit.HOURS.between(oldDate, newDate);
        long minutes = ChronoUnit.MINUTES.between(oldDate, newDate);
        long seconds = ChronoUnit.SECONDS.between(oldDate, newDate);
        long milis = ChronoUnit.MILLIS.between(oldDate, newDate);
        long nano = ChronoUnit.NANOS.between(oldDate, newDate);

        System.out.println("\n--- Total --- ");
        System.out.println(years + " years");
        System.out.println(months + " months");
        System.out.println(weeks + " weeks");
        System.out.println(days + " days");
        System.out.println(hours + " hours");
        System.out.println(minutes + " minutes");
        System.out.println(seconds + " seconds");
        System.out.println(milis + " milis");
        System.out.println(nano + " nano");
    }

    /**
     * 以下为java8 基础转换
     * =======Date -> java8 time ========================
     * 以 LocatDateTime 为媒介做各种转换
     */

    /**
     * util包Date -> time包LocatDateTime（java8）
     * java.util.Date -> java.time.LocatDateTime
     *
     * @param date
     * @return
     */
    public static LocalDateTime dateToLocalDateTime(Date date) {
        // 或者
        // LocalDateTime localDateTime = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }

    /**
     * java.util.Date -> java.time.LocatDate
     *
     * @param date
     * @return
     */
    public static LocalDate dateToLocalDate(Date date) {
        return dateToLocalDateTime(date).toLocalDate();
    }

    /**
     * java.util.Date -> java.time.LocalTime
     *
     * @param date
     * @return
     */
    public static LocalTime dateToLocalTime(Date date) {
        return dateToLocalDateTime(date).toLocalTime();
    }


    /**
     * =======java8 time -> Date ========================
     */

    public static Date localDateTimeToDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * 格式 年月日 只包含日期
     *
     * @param localDate
     * @return
     */
    public static Date localDateToDate(LocalDate localDate) {
        return localDateTimeToDate(localDate.atStartOfDay());
    }

    /**
     * 格式 年月日 时分秒 包含日期+时间
     *
     * @param localDate
     * @param localTime
     * @return
     */
    public static Date localTimeToDate(LocalDate localDate, LocalTime localTime) {
        return localDateTimeToDate(LocalDateTime.of(localDate, localTime));
    }

}
