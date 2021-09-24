package top.futurenotfound.amanita.env;

import lombok.AllArgsConstructor;
import lombok.Getter;
import top.futurenotfound.amanita.exception.BookmarkException;
import top.futurenotfound.amanita.exception.GlobalExceptionCode;

/**
 * 用户角色类型
 *
 * @author liuzhuoming
 */
@Getter
@AllArgsConstructor
public enum UserRoleType {
    USER("USER"),
    VIP("VIP"),
    ADMIN("ADMIN"),
    ;
    private String name;

    public static UserRoleType getByName(String name) {
        if (name == null) throw new BookmarkException(GlobalExceptionCode.ENUM_ERROR);
        return switch (name) {
            case "USER" -> USER;
            case "VIP" -> VIP;
            case "ADMIN" -> ADMIN;
            default -> throw new BookmarkException(GlobalExceptionCode.ENUM_ERROR);
        };
    }
}
