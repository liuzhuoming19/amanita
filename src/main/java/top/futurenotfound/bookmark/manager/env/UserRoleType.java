package top.futurenotfound.bookmark.manager.env;

import lombok.AllArgsConstructor;
import lombok.Getter;

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
        return switch (name) {
            case "USER" -> USER;
            case "VIP" -> VIP;
            case "ADMIN" -> ADMIN;
            default -> null;
        };
    }
}
