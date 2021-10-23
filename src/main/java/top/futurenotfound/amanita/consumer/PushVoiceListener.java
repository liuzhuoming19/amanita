package top.futurenotfound.amanita.consumer;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;
import top.futurenotfound.amanita.dto.BookmarkDTO;
import top.futurenotfound.amanita.service.BookmarkService;
import top.futurenotfound.amanita.util.JsonUtil;

/**
 * 书签添加消费者
 *
 * @author liuzhuoming
 */
@AllArgsConstructor
@Component
@Slf4j
public class PushVoiceListener implements MessageListener {
    private final BookmarkService bookmarkService;

    @Override
    public void onMessage(Message message, byte[] bytes) {
        byte[] messageBytes = message.getBody();
        BookmarkDTO bookmarkDTO = JsonUtil.toObject(new String(messageBytes), BookmarkDTO.class);
        bookmarkService.save(bookmarkDTO);
    }
}