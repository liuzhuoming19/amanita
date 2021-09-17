package top.futurenotfound.bookmark.manager.helper;

import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * redis
 *
 * @author liuzhuoming
 */
@Component
@AllArgsConstructor
public class RedisHelper<T> {
    private final RedisTemplate<String, T> redisTemplate;

    public void set(String key, T value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public T get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    private void expire(String key, Long timeAmount, TimeUnit timeUnit) {
        redisTemplate.expire(key, timeAmount, timeUnit);
    }

    public void setEx(String key, T value, Long timeAmount, TimeUnit timeUnit) {
        set(key, value);
        expire(key, timeAmount, timeUnit);
    }
}
