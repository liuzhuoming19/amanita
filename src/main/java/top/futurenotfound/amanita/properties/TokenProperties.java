package top.futurenotfound.amanita.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.time.temporal.ChronoUnit;
import java.util.concurrent.TimeUnit;

/**
 * token配置参数
 *
 * @author liuzhuoming
 */
@Data
@ConfigurationProperties(prefix = "amanita.token")
@Configuration
public class TokenProperties {
    /**
     * token过期时间单位
     */
    private ChronoUnit expireTimeUnit;
    /**
     * token过期时间数值
     */
    private Long expireTimeAmount;
    /**
     * 刷新token过期时间单位
     */
    private TimeUnit refreshExpireTimeUnit;
    /**
     * 刷新token过期时间数值
     */
    private Long refreshExpireTimeAmount;
    /**
     * jwt签名key
     */
    private String signKey;
}
