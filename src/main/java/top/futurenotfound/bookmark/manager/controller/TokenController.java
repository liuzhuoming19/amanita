package top.futurenotfound.bookmark.manager.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import top.futurenotfound.bookmark.manager.domain.TokenEntity;
import top.futurenotfound.bookmark.manager.domain.User;
import top.futurenotfound.bookmark.manager.env.RedisKey;
import top.futurenotfound.bookmark.manager.exception.AuthException;
import top.futurenotfound.bookmark.manager.exception.GlobalExceptionCode;
import top.futurenotfound.bookmark.manager.helper.JwtHelper;
import top.futurenotfound.bookmark.manager.helper.RedisHelper;
import top.futurenotfound.bookmark.manager.service.UserService;
import top.futurenotfound.bookmark.manager.util.PasswordUtil;
import top.futurenotfound.bookmark.manager.util.StringUtil;

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
        TokenEntity tokenEntity = jwtHelper.create(username, user.getRole());
        return ResponseEntity.ok(tokenEntity);
    }

    @PutMapping
    @ApiOperation("刷新")
    @ApiOperationSupport(ignoreParameters = {"Source"})
    public ResponseEntity<TokenEntity> update(@RequestHeader String refreshToken) {
        String refreshTokenKey = RedisKey.REFRESH_TOKEN + refreshToken;
        String username = redisHelper.get(refreshTokenKey);
        if (StringUtil.isEmpty(username)) throw new AuthException(GlobalExceptionCode.REFRESH_TOKEN_EXPIRED);
        User user = userService.getByUsername(username);
        if (user == null) throw new AuthException(GlobalExceptionCode.USERNAME_OR_PASSWORD_NOT_MATCH);
        TokenEntity tokenEntity = jwtHelper.create(username, user.getRole());
        redisHelper.del(refreshTokenKey);
        return ResponseEntity.ok(tokenEntity);
    }
}
