package top.futurenotfound.bookmark.manager.exception;

import cn.hutool.core.text.StrFormatter;
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
    SUCCESS("00000", "success"),
    FAIL("00001", "fail"),


    TOKEN_EXPIRED("00100", "token不存在或者已过期"),
    USERNAME_WAS_USED("00101", "用户名已被使用"),
    USERNAME_OR_PASSWORD_NOT_MATCH("00102", "用户名或者密码不匹配"),
    USER_NOT_EXIST("00103", "用户不存在"),
    NO_AUTH("00104", "当前登陆用户无该数据操作权限"),
    USER_HAS_MAX_BOOKMARKS("00105", "普通用户已达到最大书签上限"),
    SOURCE_IS_REQUIRED("00106", "请求来源不能为空"),
    UNKNOWN_SOURCE("00107", "未知请求来源"),
    ACCESS_EXPIRED("00108", "access不存在或者已过期"),
    TOKEN_ERROR("00109", "token错误"),
    NO_ROUTE_ERROR("00110", "无效请求"),

    BOOKMARK_NOT_EXIST("00200", "书签不存在"),
    BOOKMARK_IS_ALREADY_EXIST("00201", "书签已存在"),
    BOOKMARK_TAG_NOT_EXIST("00201", "书签标签不存在"),


    CONTENT_EXTRACTOR_FAIL("10001", "网页正文抽取失败"),
    ;

    private String code;
    private String msg;

    @Override
    public String toString() {
        return StrFormatter.format("({}){}", code, msg);
    }
}
