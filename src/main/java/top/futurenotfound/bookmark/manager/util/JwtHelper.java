package top.futurenotfound.bookmark.manager.util;

import cn.hutool.core.text.StrFormatter;
import cn.hutool.jwt.JWTException;
import cn.hutool.jwt.JWTUtil;
import cn.hutool.jwt.RegisteredPayload;
import com.google.common.collect.ImmutableMap;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import top.futurenotfound.bookmark.manager.config.CustomProperties;
import top.futurenotfound.bookmark.manager.entity.TokenEntity;
import top.futurenotfound.bookmark.manager.env.Constant;

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
        if (StringUtils.isEmpty(token))
            throw new JWTException(StrFormatter.format("JWT 解析失败----【{}】", token));
        boolean tf = JWTUtil.verify(token, Constant.JWT_SIGN_KEY.getBytes(StandardCharsets.UTF_8));
        if (!tf)
            throw new JWTException(StrFormatter.format("JWT 解析失败----【{}】", token));
    }
}
