package top.futurenotfound.amanita.service;

import top.futurenotfound.amanita.env.UserRoleType;

/**
 * 用户角色
 *
 * @author liuzhuoming
 */
public interface UserRoleService {

    UserRoleType getByUserId(String userId);
}
