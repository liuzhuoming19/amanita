package top.futurenotfound.amanita.exception;

import lombok.Getter;

/**
 * 书签异常
 *
 * @author DK
 */
@Getter
public class MemberException extends AmanitaException {
    private static final long serialVersionUID = -5786150841339937581L;
    private final ExceptionCode exceptionCode;

    public MemberException(ExceptionCode exceptionCode) {
        super(exceptionCode.toString());
        this.exceptionCode = exceptionCode;
    }
}
