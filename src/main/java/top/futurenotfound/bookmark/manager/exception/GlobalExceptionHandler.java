package top.futurenotfound.bookmark.manager.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理
 *
 * @author liuzhuoming
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(value = BookmarkException.class)
    public ResponseEntity<String> bookmarkExceptionHandler(BookmarkException e) {
        return ResponseEntity.badRequest().body(e.getExceptionCode().toString());
    }
}
