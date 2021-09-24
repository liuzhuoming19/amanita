package top.futurenotfound.amanita.exception;

import lombok.Getter;

import java.io.Serial;

/**
 * 书签异常
 *
 * @author liuzhuoming
 */
@Getter
public class BookmarkException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = -5786150841339936581L;
    private final ExceptionCode exceptionCode;

    public BookmarkException(ExceptionCode exceptionCode) {
        super(exceptionCode.toString());
        this.exceptionCode = exceptionCode;
    }
}
