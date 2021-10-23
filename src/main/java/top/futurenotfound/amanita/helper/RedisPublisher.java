package top.futurenotfound.amanita.helper;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import top.futurenotfound.amanita.dto.BookmarkDTO;
import top.futurenotfound.amanita.env.RedisTopic;

/**
 * redis发布者
 *
 * @author liuzhuoming
 */
@Component
@AllArgsConstructor
@Slf4j
public class RedisPublisher {
    private final RedisTemplate<String, Object> redisTemplate;

    public void sendToMessageQueue(BookmarkDTO bookmarkDTO) {
        redisTemplate.convertAndSend(RedisTopic.BOOKMARK_ADD_TOPIC, bookmarkDTO);
    }
}
