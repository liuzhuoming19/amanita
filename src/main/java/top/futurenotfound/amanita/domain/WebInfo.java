package top.futurenotfound.amanita.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 网页基本信息
 *
 * @author liuzhuoming
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WebInfo implements Serializable {
    private static final long serialVersionUID = -6119913150703528350L;
    /**
     * url
     */
    private String url;
    /**
     * 域名
     */
    private String host;
    /**
     * 标题
     */
    private String title;
}
