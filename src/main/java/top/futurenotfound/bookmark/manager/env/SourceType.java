package top.futurenotfound.bookmark.manager.env;

/**
 * 请求来源
 *
 * @author wxkj0012
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
        switch (name) {
            case "WEB":
                return WEB;
            case "API":
                return API;
            default:
                return null;
        }
    }
}
