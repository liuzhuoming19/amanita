package top.futurenotfound.bookmark.manager.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.futurenotfound.bookmark.manager.domain.Result;
import top.futurenotfound.bookmark.manager.domain.User;
import top.futurenotfound.bookmark.manager.exception.BookmarkException;
import top.futurenotfound.bookmark.manager.exception.ExceptionCode;
import top.futurenotfound.bookmark.manager.service.UserService;
import top.futurenotfound.bookmark.manager.util.JwtUtil;
import top.futurenotfound.bookmark.manager.util.PasswordUtil;

import java.util.Objects;

/**
 * 登陆controller
 *
 * @author liuzhuoming
 */
@RestController
@RequestMapping("login")
@Api(value = "LoginController", tags = "登陆Controller")
@AllArgsConstructor
public class LoginController {
    private final UserService userService;

    @GetMapping
    @ApiOperation("登陆")
    public Result<String> login(@RequestParam String username,
                                @RequestParam String password) {
        User user = userService.getByUsername(username);
        if (user == null || Objects.equals(PasswordUtil.compute(password, username), user.getPassword())) {
            throw new BookmarkException(ExceptionCode.USERNAME_OR_PASSWORD_NOT_MATCH);
        }
        String jwt = JwtUtil.create(username, user.getRole());
        return new Result<String>(ExceptionCode.SUCC).data(jwt);
    }
}
