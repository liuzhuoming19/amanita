package top.futurenotfound.bookmark.manager.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 异常编码
 *
 * @author liuzhuoming
 */
@Getter
@AllArgsConstructor
public enum ExceptionCode {
    SUCC("00000", "success"),
    FAIL("00001", "fail"),


    TOKEN_EXPIRED("00100", "token不存在或者已过期"),
    USERNAME_WAS_USED("00101", "用户名已被使用"),
    USERNAME_OR_PASSWORD_NOT_MATCH("00102", "用户名或者密码不匹配"),
    ;

    private String code;
    private String msg;
}
