package top.futurenotfound.bookmark.manager.env;

import lombok.AllArgsConstructor;
import lombok.Getter;
import top.futurenotfound.bookmark.manager.exception.BookmarkException;
import top.futurenotfound.bookmark.manager.exception.GlobalExceptionCode;

/**
 * 书签查询类型
 *
 * @author liuzhuoming
 */
@AllArgsConstructor
@Getter
public enum BookmarkSearchType {
    /**
     * 普通查询
     */
    NORMAL(0),
    /**
     * 查询加星收藏
     */
    STAR(1),
    /**
     * 查询已删除（回收站
     * <p>
     * 仅限vip
     */
    DELETE(2),
    ;

    private int code;

    public static BookmarkSearchType getByCode(Integer code) {
        if (code == null) throw new BookmarkException(GlobalExceptionCode.ENUM_ERROR);
        return switch (code) {
            case 0 -> NORMAL;
            case 1 -> STAR;
            case 2 -> DELETE;
            default -> throw new BookmarkException(GlobalExceptionCode.ENUM_ERROR);
        };
    }
}
