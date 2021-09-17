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
        switch (name) {
            case "USER":
                return USER;
            case "VIP":
                return VIP;
            case "ADMIN":
                return ADMIN;
            default:
                return null;
        }
    }
}
