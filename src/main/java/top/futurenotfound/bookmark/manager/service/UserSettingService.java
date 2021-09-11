package top.futurenotfound.bookmark.manager.service;

import com.baomidou.mybatisplus.extension.service.IService;
import top.futurenotfound.bookmark.manager.domain.UserSetting;

/**
 * 用户设置
 *
 * @author liuzhuoming
 */
public interface UserSettingService extends IService<UserSetting> {

    UserSetting getByUserId(String userId);
}
