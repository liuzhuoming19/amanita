package top.futurenotfound.amanita.service;

import top.futurenotfound.amanita.domain.User;
import top.futurenotfound.amanita.dto.UserPasswordDTO;

/**
 * 用户
 *
 * @author liuzhuoming
 */
public interface UserService {
    User getById(String id);

    User getByUsername(String username);

    User save(User user);

    /**
     * 更新密码
     */
    User updatePassword(UserPasswordDTO userPasswordDTO);
}
