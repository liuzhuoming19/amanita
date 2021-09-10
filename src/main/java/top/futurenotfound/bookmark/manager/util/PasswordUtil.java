package top.futurenotfound.bookmark.manager.util;

import cn.hutool.crypto.digest.BCrypt;

/**
 * 密码工具类（Bcrypt加密）
 *
 * @author liuzhuoming
 */
public class PasswordUtil {
    private PasswordUtil() {

    }

    public static String compute(String origPassword) {
        String salt = BCrypt.gensalt(4);
        return BCrypt.hashpw(origPassword, salt);
    }

    public static boolean verify(String origPassword, String encryptPassword) {
        return BCrypt.checkpw(origPassword, encryptPassword);
    }
}
