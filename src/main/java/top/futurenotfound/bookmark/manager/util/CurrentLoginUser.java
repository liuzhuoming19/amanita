package top.futurenotfound.bookmark.manager.util;

import top.futurenotfound.bookmark.manager.domain.User;
import top.futurenotfound.bookmark.manager.exception.AuthException;
import top.futurenotfound.bookmark.manager.exception.ExceptionCode;

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
        User user = userThreadLocal.get();
        if (user == null) throw new AuthException(ExceptionCode.USER_NOT_EXIST);
        return user;
    }

    public static void set(User user) {
        userThreadLocal.set(user);
    }

    public static void remove() {
        userThreadLocal.remove();
    }
}
