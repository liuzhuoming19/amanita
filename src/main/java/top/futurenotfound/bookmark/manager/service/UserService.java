package top.futurenotfound.bookmark.manager.service;

import com.baomidou.mybatisplus.extension.service.IService;
import top.futurenotfound.bookmark.manager.domain.User;

/**
 * 用户
 *
 * @author liuzhuoming
 */
public interface UserService extends IService<User> {

    User getByUsername(String username);

    /**
     * 获取脱敏后的用户信息
     * <p>
     * 去除密码信息
     *
     * @param username 用户名
     */
    User getDesensitizedUserByUserName(String username);
}
