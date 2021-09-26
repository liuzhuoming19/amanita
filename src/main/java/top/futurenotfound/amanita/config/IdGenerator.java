package top.futurenotfound.amanita.config;

import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import org.springframework.stereotype.Component;
import top.futurenotfound.amanita.exception.BookmarkException;
import top.futurenotfound.amanita.exception.GlobalExceptionCode;

import java.util.UUID;

/**
 * 数据库id生成器
 *
 * @author liuzhuoming
 */
@Component
public class IdGenerator implements IdentifierGenerator {
    @Override
    @Deprecated
    public Number nextId(Object entity) {
        throw new BookmarkException(GlobalExceptionCode.ID_GENERATE_ERROR);
    }

    @Override
    public String nextUUID(Object entity) {
        return UUID.randomUUID().toString();
    }
}
