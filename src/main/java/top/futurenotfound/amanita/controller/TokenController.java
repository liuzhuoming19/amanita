package top.futurenotfound.amanita.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import top.futurenotfound.amanita.domain.TokenEntity;
import top.futurenotfound.amanita.domain.User;
import top.futurenotfound.amanita.env.RedisKey;
import top.futurenotfound.amanita.exception.AuthException;
import top.futurenotfound.amanita.exception.GlobalExceptionCode;
import top.futurenotfound.amanita.helper.JwtHelper;
import top.futurenotfound.amanita.helper.RedisHelper;
import top.futurenotfound.amanita.service.UserService;
import top.futurenotfound.amanita.util.PasswordUtil;
import top.futurenotfound.amanita.util.StringUtil;

import javax.validation.Valid;

/**
 * token controller
 *
 * @author liuzhuoming
 */
@Controller
@RequestMapping("token")
@Api(value = "TokenController", tags = "Token Controller")
@AllArgsConstructor
public class TokenController {
    private final UserService userService;
    private final JwtHelper jwtHelper;
    private final RedisHelper<String> redisHelper;

    @GetMapping
    @ApiOperation("获取")
    @ApiOperationSupport(ignoreParameters = {"Source"})
    public ResponseEntity<TokenEntity> get(@RequestParam String username,
                                           @RequestParam String password) {
        User user = userService.getByUsername(username);
        if (user == null || !PasswordUtil.verify(password, user.getPassword())) {
            throw new AuthException(GlobalExceptionCode.USERNAME_OR_PASSWORD_NOT_MATCH);
        }
        if (user.getEnabled() == 0) throw new AuthException(GlobalExceptionCode.USER_IS_NOT_ENABLE);
        TokenEntity tokenEntity = jwtHelper.create(username, user.getRole());
        return ResponseEntity.ok(tokenEntity);
    }

    @PutMapping
    @ApiOperation("刷新")
    @ApiOperationSupport(ignoreParameters = {"Source"})
    public ResponseEntity<TokenEntity> update(@Valid @RequestBody TokenEntity refreshEntity) {
        String refreshToken = refreshEntity.getRefreshToken();
        String accessToken = refreshEntity.getAccessToken();

        String username = jwtHelper.getUsername(accessToken);
        if (StringUtil.isEmpty(username)) throw new AuthException(GlobalExceptionCode.REFRESH_TOKEN_EXPIRED);

        String refreshTokenKey = RedisKey.REFRESH_TOKEN + username;
        String refreshTokenJwt = redisHelper.get(refreshTokenKey);

        if (!StringUtil.equals(refreshTokenJwt, refreshToken))
            throw new AuthException(GlobalExceptionCode.REFRESH_TOKEN_EXPIRED);

        User user = userService.getByUsername(username);
        if (user == null) throw new AuthException(GlobalExceptionCode.USERNAME_OR_PASSWORD_NOT_MATCH);
        TokenEntity tokenEntity = jwtHelper.create(username, user.getRole());
        redisHelper.del(refreshTokenKey);
        return ResponseEntity.ok(tokenEntity);
    }
}
