package top.futurenotfound.amanita.env;

import top.futurenotfound.amanita.exception.BookmarkException;
import top.futurenotfound.amanita.exception.GlobalExceptionCode;

/**
 * 请求来源
 *
 * @author liuzhuoming
 */
public enum SourceType {
    /**
     * web请求
     * <p>
     * jwt认证
     */
    WEB,
    /**
     * 外部api请求
     * <p>
     * access认证
     */
    API,
    ;

    public static SourceType getByName(String name) {
        if (name == null) throw new BookmarkException(GlobalExceptionCode.ENUM_ERROR);
        switch (name) {
            case "WEB":
                return WEB;
            case "API":
                return API;
            default:
                throw new BookmarkException(GlobalExceptionCode.ENUM_ERROR);
        }
    }
}
