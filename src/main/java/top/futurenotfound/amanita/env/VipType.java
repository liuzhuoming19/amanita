package top.futurenotfound.amanita.env;

import lombok.AllArgsConstructor;
import lombok.Getter;
import top.futurenotfound.amanita.exception.BookmarkException;
import top.futurenotfound.amanita.exception.GlobalExceptionCode;

/**
 * 会员类型
 *
 * @author liuzhuoming
 */
@Getter
@AllArgsConstructor
public enum VipType {
    /**
     * 捐赠
     */
    DONATE(0),
    /**
     * 免费
     * <p>
     * 赠送，兑换等
     */
    FREE(1),
    /**
     * 付款
     */
    PAY(2),
    ;

    private Integer code;

    public static VipType getByCode(Integer code) {
        if (code == null) throw new BookmarkException(GlobalExceptionCode.ENUM_ERROR);
        switch (code) {
            case 0:
                return DONATE;
            case 1:
                return FREE;
            case 2:
                return PAY;
            default:
                throw new BookmarkException(GlobalExceptionCode.ENUM_ERROR);
        }
    }
}
