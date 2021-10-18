package top.futurenotfound.amanita.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import top.futurenotfound.amanita.env.UrlAuthMethodType;

/**
 * url权限
 *
 * @author liuzhuoming
 */
@Data
@AllArgsConstructor
public class UrlAuth {
    /**
     * 请求类型
     * <p>
     * POST，GET，PUT，DELETE
     * <p>
     * ALL 代表无视请求类型
     *
     * @see UrlAuthMethodType
     */
    private UrlAuthMethodType method;
    /**
     * url
     */
    private String url;
}
