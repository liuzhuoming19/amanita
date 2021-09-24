package top.futurenotfound.amanita.service;

import top.futurenotfound.amanita.domain.UserAdmin;

/**
 * 用户管理员
 *
 * @author liuzhuoming
 */
public interface UserAdminService {

    UserAdmin getByUserId(String userId);
}
