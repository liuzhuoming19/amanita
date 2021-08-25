package top.futurenotfound.bookmark.manager.util;

import cn.hutool.core.text.StrFormatter;
import cn.hutool.jwt.JWTException;
import cn.hutool.jwt.JWTUtil;
import cn.hutool.jwt.RegisteredPayload;
import com.google.common.collect.ImmutableMap;

import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * jwt
 *
 * @author liuzhuoming
 */
public class JwtUtil {
    private static final long TOKEN_EXPIRATION = 24 * 60 * 60 * 1000;
    private static final String SIGN_KEY = "i_am_superman!";
    private static final String ROLE = "role";
    private static final String USERNAME = "username";

    private JwtUtil() {
    }

    public static String create(String username, String role) {
        ImmutableMap<String, Object> payload = ImmutableMap.of(
                ROLE, role,
                USERNAME, username,
                RegisteredPayload.EXPIRES_AT, new Date(System.currentTimeMillis() + TOKEN_EXPIRATION));
        return JWTUtil.createToken(payload, SIGN_KEY.getBytes(StandardCharsets.UTF_8));
    }

    public static String getUsername(String token) {
        verify(token);
        return JWTUtil.parseToken(token).getPayload(USERNAME).toString();
    }

    public static String getRole(String token) {
        verify(token);
        return JWTUtil.parseToken(token).getPayload(ROLE).toString();
    }

    private static void verify(String token) {
        boolean tf = JWTUtil.verify(token, SIGN_KEY.getBytes(StandardCharsets.UTF_8));
        if (!tf) {
            throw new JWTException(StrFormatter.format("JWT 解析失败----【{}】", token));
        }
    }
}
