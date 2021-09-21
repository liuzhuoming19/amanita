package top.futurenotfound.bookmark.manager.util;

import cn.hutool.core.text.StrFormatter;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

/**
 * 字符串工具类
 *
 * @author liuzhuoming
 */
public class StringUtil {
    private StringUtil() {

    }

    public static boolean isEmpty(String str) {
        return StringUtils.isEmpty(str);
    }

    public static boolean isNotEmpty(String str) {
        return StringUtils.isNotEmpty(str);
    }

    public static boolean equals(String str1, String str2) {
        return Objects.equals(str1, str2);
    }

    /**
     * 格式化字符串，格式为 xxx{}xxx{}...
     *
     * @param origStr 原始字符串
     * @param args    参数数组
     */
    public static String format(String origStr, Object... args) {
        return StrFormatter.format(origStr, args);
    }
}
