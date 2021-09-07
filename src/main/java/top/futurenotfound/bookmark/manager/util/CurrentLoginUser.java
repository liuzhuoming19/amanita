package top.futurenotfound.bookmark.manager.util;

import top.futurenotfound.bookmark.manager.domain.User;

/**
 * 当前登陆用户信息
 *
 * @author liuzhuoming
 */
public class CurrentLoginUser {
    private static final ThreadLocal<User> userThreadLocal = new ThreadLocal<>();

    private CurrentLoginUser() {
    }

    public static User get() {
        return userThreadLocal.get();
    }

    public static void set(User user) {
        userThreadLocal.set(user);
    }

    public static void remove() {
        userThreadLocal.remove();
    }
}
