package top.futurenotfound.amanita.exception;

import lombok.Getter;

import java.io.Serial;

/**
 * 认证异常
 *
 * @author liuzhuoming
 */
@Getter
public class AuthException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = -5786150841339936581L;
    private final ExceptionCode exceptionCode;

    public AuthException(ExceptionCode exceptionCode) {
        super(exceptionCode.toString());
        this.exceptionCode = exceptionCode;
    }
}
