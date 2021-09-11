package top.futurenotfound.bookmark.manager.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.futurenotfound.bookmark.manager.domain.UserAdmin;
import top.futurenotfound.bookmark.manager.mapper.UserAdminMapper;
import top.futurenotfound.bookmark.manager.service.UserAdminService;

/**
 * 用户管理员
 *
 * @author liuzhuoming
 */
@Service
public class UserAdminServiceImpl extends ServiceImpl<UserAdminMapper, UserAdmin>
        implements UserAdminService {

    @Override
    public UserAdmin getByUserId(String userId) {
        LambdaQueryWrapper<UserAdmin> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserAdmin::getUserId, userId);
        return this.baseMapper.selectOne(queryWrapper);
    }
}




