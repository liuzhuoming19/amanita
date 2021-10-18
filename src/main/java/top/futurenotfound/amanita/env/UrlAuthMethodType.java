package top.futurenotfound.amanita.env;

/**
 * UrlAuthMethodType
 *
 * @author liuzhuoming
 */
public enum UrlAuthMethodType {
    ALL,
    GET,
    POST,
    PUT,
    DELETE,
    ;

    public static UrlAuthMethodType getByName(String name) {
        if (name == null) return null;
        return switch (name) {
            case "ALL" -> ALL;
            case "GET" -> GET;
            case "POST" -> POST;
            case "PUT" -> PUT;
            case "DELETE" -> DELETE;
            default -> null;
        };
    }
}
