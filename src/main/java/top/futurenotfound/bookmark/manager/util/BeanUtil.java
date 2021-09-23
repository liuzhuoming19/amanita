package top.futurenotfound.bookmark.manager.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import top.futurenotfound.bookmark.manager.exception.BookmarkException;
import top.futurenotfound.bookmark.manager.exception.GlobalExceptionCode;

import java.lang.reflect.InvocationTargetException;

/**
 * bean工具类
 *
 * @author liuzhuoming
 */
@Slf4j
public class BeanUtil {
    private BeanUtil() {
    }

    public static <T> T convert(Object o, Class<T> target) {
        try {
            T t = target.getDeclaredConstructor().newInstance();
            BeanUtils.copyProperties(o, t);
            return t;
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            log.error("{}", e);
            throw new BookmarkException(GlobalExceptionCode.BEAN_ERROR);
        }
    }
}
