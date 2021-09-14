package top.futurenotfound.bookmark.manager.service;

import top.futurenotfound.bookmark.manager.domain.UserAdmin;

/**
 * 用户管理员
 *
 * @author liuzhuoming
 */
public interface UserAdminService {

    UserAdmin getByUserId(String userId);
}
