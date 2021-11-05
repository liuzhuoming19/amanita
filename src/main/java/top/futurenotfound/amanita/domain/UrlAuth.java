package top.futurenotfound.amanita.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import top.futurenotfound.amanita.env.UrlAuthMethodType;

import java.io.Serial;
import java.io.Serializable;

/**
 * url权限
 *
 * @author liuzhuoming
 */
@Data
@AllArgsConstructor
public class UrlAuth implements Serializable {
    @Serial
    private static final long serialVersionUID = 548265579936148246L;
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
