package top.futurenotfound.amanita.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 自定义配置参数
 *
 * @author liuzhuoming
 */
@Component
@ConfigurationProperties(prefix = "amanita")
@Data
public class AmanitaProperties {
    private TokenProperties token;
    private AccessProperties access;
    private BookmarkProperties bookmark;
}
