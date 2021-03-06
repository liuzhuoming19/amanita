package top.futurenotfound.amanita.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import top.futurenotfound.amanita.domain.UserAdmin;
import top.futurenotfound.amanita.mapper.UserAdminMapper;
import top.futurenotfound.amanita.service.UserAdminService;

/**
 * 用户管理员
 *
 * @author liuzhuoming
 */
@Service
@AllArgsConstructor
public class UserAdminServiceImpl implements UserAdminService {
    private final UserAdminMapper userAdminMapper;

    @Override
    public UserAdmin getByUserId(String userId) {
        LambdaQueryWrapper<UserAdmin> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserAdmin::getUserId, userId);
        return userAdminMapper.selectOne(queryWrapper);
    }
}




