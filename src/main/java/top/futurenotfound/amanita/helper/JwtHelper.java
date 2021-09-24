package top.futurenotfound.amanita.helper;

import cn.hutool.jwt.JWTException;
import cn.hutool.jwt.JWTUtil;
import cn.hutool.jwt.RegisteredPayload;
import com.google.common.collect.ImmutableMap;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import top.futurenotfound.amanita.config.AmanitaProperties;
import top.futurenotfound.amanita.domain.TokenEntity;
import top.futurenotfound.amanita.env.Constant;
import top.futurenotfound.amanita.env.RedisKey;
import top.futurenotfound.amanita.exception.AuthException;
import top.futurenotfound.amanita.exception.GlobalExceptionCode;
import top.futurenotfound.amanita.util.DateUtil;
import top.futurenotfound.amanita.util.RandomStringUtil;
import top.futurenotfound.amanita.util.StringUtil;

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
    private final AmanitaProperties amanitaProperties;
    private final RedisHelper<String> redisHelper;

    public TokenEntity create(String username, String role) {
        String tokenSignKey = amanitaProperties.getTokenSignKey();
        if (StringUtil.isEmpty(tokenSignKey)) throw new AuthException(GlobalExceptionCode.JWT_SIGN_KEY_IS_REQUIRED);

        Date now = DateUtil.now();
        Date expiredTime = DateUtil.add(now,
                amanitaProperties.getTokenExpireTimeUnit(), amanitaProperties.getTokenExpireTimeAmount());
        ImmutableMap<String, Object> payload = ImmutableMap.of(
                Constant.JWT_ROLE, role,
                Constant.JWT_USERNAME, username,
                RegisteredPayload.EXPIRES_AT, expiredTime);

        String accessToken = JWTUtil.createToken(payload, tokenSignKey.getBytes(StandardCharsets.UTF_8));
        String refreshToken = RandomStringUtil.generateRandomString(32);

        redisHelper.setEx(RedisKey.REFRESH_TOKEN + refreshToken, username,
                amanitaProperties.getRefreshTokenExpireTimeAmount(), amanitaProperties.getRefreshTokenExpireTimeUnit());

        return new TokenEntity(accessToken, refreshToken, now, expiredTime);
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
        String tokenSignKey = amanitaProperties.getTokenSignKey();
        if (StringUtil.isEmpty(tokenSignKey)) throw new AuthException(GlobalExceptionCode.JWT_SIGN_KEY_IS_REQUIRED);

        if (StringUtil.isEmpty(token))
            throw new JWTException(StringUtil.format("JWT 解析失败----【{}】", token));
        boolean tf = JWTUtil.verify(token, tokenSignKey.getBytes(StandardCharsets.UTF_8));
        if (!tf)
            throw new JWTException(StringUtil.format("JWT 解析失败----【{}】", token));
    }
}
