package top.futurenotfound.bookmark.manager.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import top.futurenotfound.bookmark.manager.util.StringUtil;

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
        log.error(StringUtil.format("未处理异常: {}", e));
        return ResponseEntity
                .badRequest()
                .body(GlobalExceptionCode.FAIL.toString());
    }

    @ExceptionHandler(value = BookmarkException.class)
    public ResponseEntity<String> bookmarkExceptionHandler(BookmarkException e) {
        log.error(StringUtil.format("书签异常: {}", e.getExceptionCode().toString()));
        return ResponseEntity
                .badRequest()
                .body(e.getExceptionCode().toString());
    }

    @ExceptionHandler(value = AuthException.class)
    public ResponseEntity<String> authExceptionHandler(AuthException e) {
        log.error(StringUtil.format("认证异常: {}", e.getExceptionCode().toString()));
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
            if (objects == null) return null;
            DefaultMessageSourceResolvable defaultMessageSourceResolvable = (DefaultMessageSourceResolvable) objects[0];
            String paramName = defaultMessageSourceResolvable.getDefaultMessage();
            return paramName + objectError.getDefaultMessage();
        }).collect(Collectors.joining(","));
        log.error(StringUtil.format("入参异常: {}", errors));
        ExceptionCode exceptionCode = new DetailExceptionCode(GlobalExceptionCode.PARAMETER_ERROR.getCode(), errors);
        return ResponseEntity
                .badRequest()
                .body(exceptionCode.toString());
    }
}
