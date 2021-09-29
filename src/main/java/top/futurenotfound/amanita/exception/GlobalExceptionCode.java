package top.futurenotfound.amanita.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import top.futurenotfound.amanita.util.JsonUtil;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 全局异常编码
 *
 * @author liuzhuoming
 */
@Getter
@AllArgsConstructor
public enum GlobalExceptionCode implements ExceptionCode {
    SUCCESS("00000", "success"),
    FAIL("00001", "fail"),

    TOKEN_EXPIRED("00100", "token不存在或者已过期"),
    USERNAME_WAS_USED("00101", "用户名已被使用"),
    USERNAME_OR_PASSWORD_NOT_MATCH("00102", "用户名或者密码不匹配"),
    USER_NOT_EXIST("00103", "用户不存在"),
    NO_AUTH("00104", "当前登陆用户无该操作权限"),
    USER_HAS_MAX_BOOKMARKS("00105", "普通用户已达到最大书签上限"),
    SOURCE_IS_REQUIRED("00106", "请求来源不能为空"),
    UNKNOWN_SOURCE("00107", "未知请求来源"),
    ACCESS_EXPIRED("00108", "access不存在或者已过期"),
    TOKEN_ERROR("00109", "token错误"),
    NO_ROUTE_ERROR("00110", "无效请求"),
    PARAMETER_ERROR("00111", "参数错误"),
    REFRESH_TOKEN_EXPIRED("00112", "刷新token不存在或已过期"),
    JWT_SIGN_KEY_IS_REQUIRED("00113", "jwt签名密钥不能为空"),
    ID_GENERATE_ERROR("00114", "通用id生成出现错误"),
    USER_IS_NOT_ENABLE("00105", "用户不可用"),

    BOOKMARK_NOT_EXIST("00200", "书签不存在"),
    BOOKMARK_IS_ALREADY_EXIST("00201", "书签已存在"),
    BOOKMARK_TAG_NOT_EXIST("00202", "书签标签不存在"),
    BOOKMARK_IS_ALREADY_DELETED("00203", "书签已删除"),
    BOOKMARK_TAG_IS_ALREADY_EXIST("00204", "书签标签已存在"),


    CONTENT_EXTRACTOR_FAIL("10001", "网页正文抽取失败"),

    BEAN_ERROR("11000", "bean操作错误"),
    ENUM_ERROR("11001", "enum操作错误"),
    ;

    private String code;
    private String msg;

    @Override
    public String toString() {
        Map<String, String> r = new LinkedHashMap<>(2);
        r.put("code", code);
        r.put("msg", msg);
        return JsonUtil.toJson(r);
    }
}
