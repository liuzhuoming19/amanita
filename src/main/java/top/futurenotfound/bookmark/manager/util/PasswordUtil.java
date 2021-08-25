package top.futurenotfound.bookmark.manager.util;

import cn.hutool.crypto.digest.MD5;

import java.nio.charset.StandardCharsets;

/**
 * 密码工具类
 *
 * @author liuzhuoming
 */
public class PasswordUtil {
    private PasswordUtil() {

    }

    public static String compute(String origPassword, String salt) {
        MD5 md5 = MD5.create();
        md5.setSalt(salt.getBytes(StandardCharsets.UTF_8));
        md5.setDigestCount(100);
        return new String(md5.digest(origPassword));
    }
}
