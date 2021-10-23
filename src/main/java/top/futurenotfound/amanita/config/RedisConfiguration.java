package top.futurenotfound.amanita.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import top.futurenotfound.amanita.consumer.PushVoiceListener;
import top.futurenotfound.amanita.env.RedisTopic;
import top.futurenotfound.amanita.service.BookmarkService;

/**
 * redis配置
 *
 * @author liuzhuoming
 */
@Configuration
@Order(2)
public class RedisConfiguration {

    @Bean
    public RedisTemplate<String, Object> redisTemplate(LettuceConnectionFactory factory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(factory);
        JdkSerializationRedisSerializer jdkSerializationRedisSerializer = new JdkSerializationRedisSerializer();
        redisTemplate.setValueSerializer(jdkSerializationRedisSerializer);
        redisTemplate.setKeySerializer(jdkSerializationRedisSerializer);
        redisTemplate.setHashKeySerializer(jdkSerializationRedisSerializer);
        redisTemplate.setHashValueSerializer(jdkSerializationRedisSerializer);
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    @Bean
    RedisMessageListenerContainer pushBookmarkAddListenerContainer(RedisConnectionFactory connectionFactory,
                                                                   MessageListenerAdapter bookmarkAddAdapter) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.addMessageListener(bookmarkAddAdapter, new PatternTopic(RedisTopic.BOOKMARK_ADD_TOPIC));
        return container;
    }

    @Bean
    MessageListenerAdapter bookmarkAddAdapter(BookmarkService bookmarkService) {
        return new MessageListenerAdapter(new PushVoiceListener(bookmarkService));
    }
}
