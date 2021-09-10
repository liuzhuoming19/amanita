package top.futurenotfound.bookmark.manager.util;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.TemporalUnit;
import java.util.Date;

/**
 * 时间工具类
 *
 * @author liuzhuoming
 */
public class DateUtil {
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

    public static Date add(Date date, TemporalUnit temporalUnit, long amountToAdd) {
        LocalDateTime localDateTime = dateToLocalDateTime(date);
        localDateTime = localDateTime.plus(amountToAdd, temporalUnit);
        return localDateTimeToDate(localDateTime);
    }

}
