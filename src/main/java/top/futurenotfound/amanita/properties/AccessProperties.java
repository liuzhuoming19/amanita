package top.futurenotfound.amanita.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * access配置参数
 *
 * @author liuzhuoming
 */
@Data
@ConfigurationProperties(prefix = "amanita.access")
@Configuration
public class AccessProperties {
    /**
     * access key长度
     */
    private Integer keyLength;
    /**
     * access secret长度
     */
    private Integer secretLength;
    /**
     * access过期天数
     */
    private Integer expireDays;
}
