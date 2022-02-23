package top.futurenotfound.amanita.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 网页html信息
 *
 * @author liuzhuoming
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@ToString(callSuper = true)
public class WebHtmlInfo extends WebInfo {
    private static final long serialVersionUID = 8280571998748200450L;
    /**
     * 网页html
     */
    private String html;

    public WebHtmlInfo(String url, String host, String title, String html) {
        super(url, host, title);
        this.html = html;
    }
}
