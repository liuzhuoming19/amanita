package top.futurenotfound.bookmark.manager.env;

import lombok.AllArgsConstructor;
import lombok.Getter;

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
        return switch (code) {
            case 0 -> DONATE;
            case 1 -> FREE;
            case 2 -> PAY;
            default -> null;
        };
    }
}
