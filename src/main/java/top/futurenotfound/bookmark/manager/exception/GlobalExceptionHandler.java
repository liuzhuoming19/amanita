package top.futurenotfound.bookmark.manager.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import top.futurenotfound.bookmark.manager.domain.Result;

/**
 * 全局异常处理
 *
 * @author liuzhuoming
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(value = BookmarkException.class)
    public Result<String> bookmarkExceptionHandler(BookmarkException e) {
        return new Result<>(e.getCode());
    }
}
