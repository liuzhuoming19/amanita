package top.futurenotfound.bookmark.manager.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 网页正文摘要信息
 *
 * @author liuzhuoming
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@ToString(callSuper = true)
public class WebExcerptInfo extends WebInfo {
    /**
     * 摘要
     */
    private String excerpt;
    private String firstImageUrl;

    public WebExcerptInfo(String url, String host, String title, String excerpt, String firstImageUrl) {
        super(url, host, title);
        this.excerpt = excerpt;
        this.firstImageUrl = firstImageUrl;
    }
}
