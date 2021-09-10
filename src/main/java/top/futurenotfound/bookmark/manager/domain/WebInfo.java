package top.futurenotfound.bookmark.manager.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 网页基本信息
 *
 * @author liuzhuoming
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WebInfo {
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
