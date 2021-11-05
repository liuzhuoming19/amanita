package top.futurenotfound.amanita.helper;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import top.futurenotfound.amanita.dto.BookmarkDTO;
import top.futurenotfound.amanita.env.RedisTopic;
import top.futurenotfound.amanita.util.JsonUtil;

/**
 * redis发布者
 *
 * @author liuzhuoming
 */
@Component
@AllArgsConstructor
@Slf4j
public class RedisPublisher {
    private final StringRedisTemplate redisTemplate;

    public void sendToMessageQueue(BookmarkDTO bookmarkDTO) {
        redisTemplate.convertAndSend(RedisTopic.BOOKMARK_ADD_TOPIC, JsonUtil.toJson(bookmarkDTO));
    }
}
