package top.futurenotfound.bookmark.manager.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import top.futurenotfound.bookmark.manager.domain.UserSetting;
import top.futurenotfound.bookmark.manager.mapper.UserSettingMapper;
import top.futurenotfound.bookmark.manager.service.UserSettingService;

/**
 * 用户设置
 *
 * @author liuzhuoming
 */
@Service
@AllArgsConstructor
@Slf4j
public class UserSettingServiceImpl extends ServiceImpl<UserSettingMapper, UserSetting>
        implements UserSettingService {

    @Override
    public UserSetting getByUserId(String userId) {
        LambdaQueryWrapper<UserSetting> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserSetting::getUserId, userId);
        return this.baseMapper.selectOne(queryWrapper);
    }

    @Override
    public boolean save(UserSetting entity) {
        return super.save(entity);
    }
}




