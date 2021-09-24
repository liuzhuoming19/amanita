package top.futurenotfound.bookmark.manager.util;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 随机字符串工具类
 *
 * @author liuzhuoming
 */
public class RandomStringUtil {
    private static final String CHARS = "01234567890abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ_-";
    private static final Random rand = ThreadLocalRandom.current();

    /**
     * 生成指定长度的字符串（包含特殊字符）
     * <p>
     * 开头和结尾已做非符号处理
     *
     * @param length 长度
     */
    public static synchronized String generateRandomString(int length) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int index;
            if (i == 0 || i == length - 1) {
                index = rand.nextInt(CHARS.length() - 2);
            } else {
                index = rand.nextInt(CHARS.length());
            }
            stringBuilder.append(CHARS.charAt(index));
        }
        return stringBuilder.toString();
    }

    /**
     * 生成指定长度的字符串（不包含特殊字符）
     *
     * @param length 长度
     */
    public static synchronized String generateRandomStringWithoutSymbol(int length) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int index;
            index = rand.nextInt(CHARS.length() - 2);
            stringBuilder.append(CHARS.charAt(index));
        }
        return stringBuilder.toString();
    }
}
