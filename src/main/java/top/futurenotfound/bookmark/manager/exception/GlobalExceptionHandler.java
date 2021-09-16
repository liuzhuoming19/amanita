package top.futurenotfound.bookmark.manager.exception;

import cn.hutool.core.text.StrFormatter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 全局异常处理
 *
 * @author liuzhuoming
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    private ObjectError objectError;

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

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<String> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        List<ObjectError> objectErrors = bindingResult.getAllErrors();
        String errors = objectErrors.stream().map(objectError -> {
            Object[] objects = objectError.getArguments();
            DefaultMessageSourceResolvable defaultMessageSourceResolvable = (DefaultMessageSourceResolvable) objects[0];
            String paramName = defaultMessageSourceResolvable.getDefaultMessage();
            return paramName + objectError.getDefaultMessage();
        }).collect(Collectors.joining(","));
        return ResponseEntity
                .badRequest()
                .body(StrFormatter.format("入参异常: {}", errors));
    }
}
