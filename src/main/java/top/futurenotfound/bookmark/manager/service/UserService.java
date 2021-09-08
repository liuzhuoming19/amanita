package top.futurenotfound.bookmark.manager.service;

import com.baomidou.mybatisplus.extension.service.IService;
import top.futurenotfound.bookmark.manager.entity.User;

/**
 * 用户
 */
public interface UserService extends IService<User> {

    User getByUsername(String username);
}
