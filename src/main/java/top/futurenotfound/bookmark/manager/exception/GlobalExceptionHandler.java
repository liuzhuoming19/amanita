package top.futurenotfound.bookmark.manager.exception;

import cn.hutool.core.text.StrFormatter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * 全局异常处理
 *
 * @author liuzhuoming
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<String> exceptionHandler(Exception e) {
        return ResponseEntity
                .badRequest()
                .body(StrFormatter.format("未处理异常: {}", e));
    }

    @ExceptionHandler(value = BookmarkException.class)
    public ResponseEntity<String> bookmarkExceptionHandler(BookmarkException e) {
        return ResponseEntity
                .badRequest()
                .body(e.getExceptionCode().toString());
    }

    @ExceptionHandler(value = AuthException.class)
    public ResponseEntity<String> authExceptionHandler(AuthException e) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(e.getExceptionCode().toString());
    }
}
