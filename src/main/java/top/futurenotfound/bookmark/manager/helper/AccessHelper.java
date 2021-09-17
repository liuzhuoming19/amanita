package top.futurenotfound.bookmark.manager.helper;

import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * access认证
 *
 * @author liuzhuoming
 */
@Component
public class AccessHelper {
    private static final String CHARS = "01234567890abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ_-";
    private static final Random rand = ThreadLocalRandom.current();

    /**
     * 生成制定长度的字符串
     *
     * @param length 长度
     */
    public synchronized String generateRandomAccess(int length) {
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
}
