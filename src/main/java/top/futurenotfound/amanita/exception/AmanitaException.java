package top.futurenotfound.amanita.exception;

/**
 * 项目中所有异常父类
 * <p>
 * 务必实现{@code AmanitaException.getExceptionCod()}方法
 *
 * @author liuzhuoming
 */
public interface AmanitaException {

    ExceptionCode getExceptionCode();
}
