package top.futurenotfound.bookmark.manager.env;

import top.futurenotfound.bookmark.manager.exception.BookmarkException;
import top.futurenotfound.bookmark.manager.exception.GlobalExceptionCode;

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
        return switch (name) {
            case "WEB" -> WEB;
            case "API" -> API;
            default -> throw new BookmarkException(GlobalExceptionCode.ENUM_ERROR);
        };
    }
}
