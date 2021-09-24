package top.futurenotfound.amanita.service;

import top.futurenotfound.amanita.domain.User;

/**
 * 用户
 *
 * @author liuzhuoming
 */
public interface UserService {
    User getById(String id);

    User getByUsername(String username);

    /**
     * 获取脱敏后的用户信息
     * <p>
     * 去除密码信息
     *
     * @param username 用户名
     */
    User getDesensitizedUserByUserName(String username);

    void save(User user);

    //TODO 缺少更新用户信息
}
