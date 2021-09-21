package top.futurenotfound.bookmark.manager.helper;

import cn.hutool.jwt.JWTException;
import cn.hutool.jwt.JWTUtil;
import cn.hutool.jwt.RegisteredPayload;
import com.google.common.collect.ImmutableMap;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import top.futurenotfound.bookmark.manager.config.CustomProperties;
import top.futurenotfound.bookmark.manager.domain.TokenEntity;
import top.futurenotfound.bookmark.manager.env.Constant;
import top.futurenotfound.bookmark.manager.util.DateUtil;
import top.futurenotfound.bookmark.manager.util.StringUtil;

import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * jwt工具
 *
 * @author liuzhuoming
 */
@Component
@AllArgsConstructor
public class JwtHelper {
    private final CustomProperties customProperties;

    public TokenEntity create(String username, String role) {
        Date now = DateUtil.now();
        Date expiredTime = DateUtil.add(now,
                customProperties.getTokenExpireTimeUnit(), customProperties.getTokenExpireTimeAmount());
        ImmutableMap<String, Object> payload = ImmutableMap.of(
                Constant.JWT_ROLE, role,
                Constant.JWT_USERNAME, username,
                RegisteredPayload.EXPIRES_AT, expiredTime);

        String token = JWTUtil.createToken(payload, Constant.JWT_SIGN_KEY.getBytes(StandardCharsets.UTF_8));
        return new TokenEntity(token, now, expiredTime);
    }

    public String getUsername(String token) {
        verify(token);
        return JWTUtil.parseToken(token).getPayload(Constant.JWT_USERNAME).toString();
    }

    public String getRole(String token) {
        verify(token);
        return JWTUtil.parseToken(token).getPayload(Constant.JWT_ROLE).toString();
    }

    public void verify(String token) {
        if (StringUtil.isEmpty(token))
            throw new JWTException(StringUtil.format("JWT 解析失败----【{}】", token));
        boolean tf = JWTUtil.verify(token, Constant.JWT_SIGN_KEY.getBytes(StandardCharsets.UTF_8));
        if (!tf)
            throw new JWTException(StringUtil.format("JWT 解析失败----【{}】", token));
    }
}
