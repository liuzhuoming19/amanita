package top.futurenotfound.bookmark.manager.exception;

import cn.hutool.core.text.StrFormatter;
import lombok.Getter;

/**
 * 书签异常
 *
 * @author liuzhuoming
 */
@Getter
public class BookmarkException extends RuntimeException {
    private static final long serialVersionUID = -5786150841339936581L;
    private final ExceptionCode code;

    public BookmarkException(ExceptionCode code) {
        super(StrFormatter.format("({}){}", code.getCode(), code.getMsg()));
        this.code = code;
    }
}
