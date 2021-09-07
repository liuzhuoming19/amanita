package top.futurenotfound.bookmark.manager.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * 异常编码
 *
 * @author liuzhuoming
 */
@Getter
@AllArgsConstructor
@ToString
public enum ExceptionCode {
    SUCCESS("00000", "success"),
    FAIL("00001", "fail"),


    TOKEN_EXPIRED("00100", "token不存在或者已过期"),
    USERNAME_WAS_USED("00101", "用户名已被使用"),
    USERNAME_OR_PASSWORD_NOT_MATCH("00102", "用户名或者密码不匹配"),

    CONTENT_EXTRACTOR_FAIL("10001", "网页正文抽取失败"),
    ;

    private String code;
    private String msg;
}
