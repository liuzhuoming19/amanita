package top.futurenotfound.bookmark.manager.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.time.temporal.ChronoUnit;
import java.util.concurrent.TimeUnit;

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
     * 刷新token过期时间单位
     */
    private TimeUnit refreshTokenExpireTimeUnit;
    /**
     * 刷新token过期时间数值
     */
    private Long refreshTokenExpireTimeAmount;

    /**
     * 书签重定向url
     */
    private String redirectUrl;

    /**
     * 普通用户书签数量上限
     */
    private Integer userBookmarkNumMax;

    /**
     * access key长度
     */
    private Integer accessKeyLength;
    /**
     * access secret长度
     */
    private Integer accessSecretLength;
    /**
     * access过期天数
     */
    private Integer accessExpireDays;
}
