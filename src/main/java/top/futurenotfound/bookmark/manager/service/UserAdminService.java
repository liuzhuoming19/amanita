package top.futurenotfound.bookmark.manager.service;

import com.baomidou.mybatisplus.extension.service.IService;
import top.futurenotfound.bookmark.manager.domain.UserAdmin;

/**
 * 用户管理员
 *
 * @author liuzhuoming
 */
public interface UserAdminService extends IService<UserAdmin> {

    UserAdmin getByUserId(String userId);
}
