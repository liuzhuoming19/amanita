package top.futurenotfound.bookmark.manager.service;

import com.baomidou.mybatisplus.extension.service.IService;
import top.futurenotfound.bookmark.manager.domain.User;

/**
 * 用户
 */
public interface UserService extends IService<User> {

    User getByUsername(String username);
}
