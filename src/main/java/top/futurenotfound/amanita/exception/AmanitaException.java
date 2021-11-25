package top.futurenotfound.amanita.exception;

import java.io.Serial;

/**
 * 项目中所有异常父类
 * <p>
 * 务必实现{@code AmanitaException.getExceptionCod()}方法
 *
 * @author liuzhuoming
 */
public abstract class AmanitaException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 7242431302288827227L;

    public AmanitaException(String message) {
        super(message);
    }

    abstract public ExceptionCode getExceptionCode();
}
