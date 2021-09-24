package top.futurenotfound.amanita.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import top.futurenotfound.amanita.domain.UserSetting;
import top.futurenotfound.amanita.mapper.UserSettingMapper;
import top.futurenotfound.amanita.service.UserSettingService;

/**
 * 用户设置
 *
 * @author liuzhuoming
 */
@Service
@AllArgsConstructor
@Slf4j
public class UserSettingServiceImpl implements UserSettingService {
    private final UserSettingMapper userSettingMapper;

    @Override
    public UserSetting getById(String id) {
        return userSettingMapper.selectById(id);
    }

    @Override
    public UserSetting getByUserId(String userId) {
        LambdaQueryWrapper<UserSetting> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserSetting::getUserId, userId);
        return userSettingMapper.selectOne(queryWrapper);
    }

    @Override
    public void save(UserSetting userSetting) {
        userSettingMapper.insert(userSetting);
    }

    @Override
    public UserSetting updateById(UserSetting userSetting) {
        userSettingMapper.updateById(userSetting);
        return userSetting;
    }
}




