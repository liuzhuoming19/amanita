package top.futurenotfound.bookmark.manager.service;

import top.futurenotfound.bookmark.manager.env.UserRoleType;

/**
 * 用户角色
 *
 * @author liuzhuoming
 */
public interface UserRoleService {

    UserRoleType getByUsername(String username);
}
