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
        switch (name) {
            case "ALL":
                return ALL;
            case "GET":
                return GET;
            case "POST":
                return POST;
            case "PUT":
                return PUT;
            case "DELETE":
                return DELETE;
            default:
                return null;
        }
    }
}
