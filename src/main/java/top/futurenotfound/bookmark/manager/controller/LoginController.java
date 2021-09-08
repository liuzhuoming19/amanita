package top.futurenotfound.bookmark.manager.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import top.futurenotfound.bookmark.manager.entity.TokenEntity;
import top.futurenotfound.bookmark.manager.entity.User;
import top.futurenotfound.bookmark.manager.exception.AuthException;
import top.futurenotfound.bookmark.manager.exception.ExceptionCode;
import top.futurenotfound.bookmark.manager.service.UserService;
import top.futurenotfound.bookmark.manager.util.JwtHelper;
import top.futurenotfound.bookmark.manager.util.PasswordUtil;

import java.util.Objects;

/**
 * 登陆controller
 *
 * @author liuzhuoming
 */
@Controller
@RequestMapping("login")
@Api(value = "LoginController", tags = "登陆Controller")
@AllArgsConstructor
public class LoginController {
    private final UserService userService;
    private final JwtHelper jwtHelper;

    @GetMapping
    @ApiOperation("登陆")
    public ResponseEntity<TokenEntity> login(@RequestParam String username,
                                             @RequestParam String password) {
        User user = userService.getByUsername(username);
        if (user == null || Objects.equals(PasswordUtil.compute(password, username), user.getPassword())) {
            throw new AuthException(ExceptionCode.USERNAME_OR_PASSWORD_NOT_MATCH);
        }
        TokenEntity tokenEntity = jwtHelper.create(username, user.getRole());
        return ResponseEntity.ok(tokenEntity);
    }
}
