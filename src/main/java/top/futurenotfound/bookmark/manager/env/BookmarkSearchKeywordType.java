package top.futurenotfound.bookmark.manager.env;

import lombok.AllArgsConstructor;
import lombok.Getter;
import top.futurenotfound.bookmark.manager.exception.BookmarkException;
import top.futurenotfound.bookmark.manager.exception.GlobalExceptionCode;

/**
 * 书签查询关键字类型
 *
 * @author liuzhuoming
 */
@AllArgsConstructor
@Getter
public enum BookmarkSearchKeywordType {
    /**
     * 书签名称
     */
    BOOKMARK(0),
    /**
     * 标签名称
     */
    TAG(1),
    ;

    private int code;

    public static BookmarkSearchKeywordType getByCode(Integer code) {
        if (code == null) throw new BookmarkException(GlobalExceptionCode.ENUM_ERROR);
        return switch (code) {
            case 0 -> BOOKMARK;
            case 1 -> TAG;
            default -> throw new BookmarkException(GlobalExceptionCode.ENUM_ERROR);
        };
    }
}
