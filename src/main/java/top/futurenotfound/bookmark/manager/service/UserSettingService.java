package top.futurenotfound.bookmark.manager.service;

import top.futurenotfound.bookmark.manager.domain.UserSetting;

/**
 * 用户设置
 *
 * @author liuzhuoming
 */
public interface UserSettingService {

    UserSetting getById(String id);

    UserSetting getByUserId(String userId);

    void save(UserSetting userSetting);

    UserSetting updateById(UserSetting userSetting);
}
