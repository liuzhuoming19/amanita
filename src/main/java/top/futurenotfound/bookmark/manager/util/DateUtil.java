package top.futurenotfound.bookmark.manager.util;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.Date;

/**
 * 时间工具类
 *
 * @author liuzhuoming
 */
public class DateUtil {
    public final static DateTimeFormatter DATETIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public final static DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public final static DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");

    private DateUtil() {
    }

    private static LocalDateTime dateToLocalDateTime(Date date) {
        return date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }

    private static Date localDateTimeToDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static Date now() {
        return new Date();
    }

    /**
     * @param date         日期
     * @param temporalUnit 单位
     * @param amountToAdd  增加的值
     * @see ChronoUnit
     */
    public static Date add(Date date, TemporalUnit temporalUnit, long amountToAdd) {
        LocalDateTime localDateTime = dateToLocalDateTime(date);
        localDateTime = localDateTime.plus(amountToAdd, temporalUnit);
        return localDateTimeToDate(localDateTime);
    }

    public static String format(Date date, DateTimeFormatter formatter) {
        return formatter.format(dateToLocalDateTime(date));
    }

    public static String formatNow(DateTimeFormatter formatter) {
        return DateUtil.format(DateUtil.now(), formatter);
    }
}
