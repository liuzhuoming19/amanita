package top.futurenotfound.amanita.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * bookmark配置参数
 *
 * @author liuzhuoming
 */
@Data
@ConfigurationProperties(prefix = "amanita.bookmark")
@Configuration
public class BookmarkProperties {
    /**
     * 书签重定向url
     */
    private String redirectUrl;
    /**
     * 普通用户书签数量上限
     */
    private Integer userNumMax;
}
