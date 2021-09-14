package top.futurenotfound.bookmark.manager.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.time.temporal.ChronoUnit;

/**
 * 自定义配置参数
 *
 * @author liuzhuoming
 */
@Component
@ConfigurationProperties(prefix = "custom")
@Data
public class CustomProperties {
    /**
     * token过期时间单位
     */
    private ChronoUnit tokenExpireTimeUnit;
    /**
     * token过期时间数值
     */
    private Long tokenExpireTimeAmount;

    /**
     * 书签重定向url
     */
    private String redirectUrl;
}
