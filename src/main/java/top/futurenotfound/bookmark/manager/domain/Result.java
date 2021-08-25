package top.futurenotfound.bookmark.manager.domain;

import lombok.Data;
import lombok.experimental.Accessors;
import top.futurenotfound.bookmark.manager.exception.ExceptionCode;

/**
 * 返回值封装
 *
 * @author liuzComing
 */
@Data
@Accessors(chain = true)
public class Result<T> {
    private String code;
    private String msg;
    private String data;

    public Result(ExceptionCode code) {
        this.code = code.getCode();
        this.msg = code.getMsg();
    }

    public Result<T> succ() {
        return new Result<>(ExceptionCode.SUCC);
    }

    public Result<T> fail() {
        return new Result<>(ExceptionCode.FAIL);
    }

    public Result<T> data(T t) {
        Result<T> result = new Result<>(ExceptionCode.SUCC);
        result.data(t);
        return result;
    }
}
