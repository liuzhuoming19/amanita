package top.futurenotfound.bookmark.manager.util;

import org.apache.commons.lang3.StringUtils;

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
}
