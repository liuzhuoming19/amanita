package top.futurenotfound.bookmark.manager.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import top.futurenotfound.bookmark.manager.domain.User;
import top.futurenotfound.bookmark.manager.domain.UserSetting;
import top.futurenotfound.bookmark.manager.env.Constant;
import top.futurenotfound.bookmark.manager.env.UserRoleType;
import top.futurenotfound.bookmark.manager.exception.BookmarkException;
import top.futurenotfound.bookmark.manager.exception.ExceptionCode;
import top.futurenotfound.bookmark.manager.mapper.UserMapper;
import top.futurenotfound.bookmark.manager.service.UserRoleService;
import top.futurenotfound.bookmark.manager.service.UserService;
import top.futurenotfound.bookmark.manager.service.UserSettingService;
import top.futurenotfound.bookmark.manager.util.DateUtil;
import top.futurenotfound.bookmark.manager.util.PasswordUtil;

import java.util.Objects;

/**
 * 用户
 *
 * @author liuzhuoming
 */
@Service
@AllArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService {
    private final UserSettingService userSettingService;
    private final UserRoleService userRoleService;

    @Override
    public User getById(String id) {
        User user = super.getById(id);
        if (user == null) {
            throw new BookmarkException(ExceptionCode.USER_NOT_EXIST);
        }
        UserRoleType userRoleType = userRoleService.getByUsername(user.getUsername());
        user.setRole(userRoleType.getName());
        return user;
    }

    @Override
    public boolean saveOrUpdate(User entity) {
        User userDb = getByUsername(entity.getUsername());
        if (userDb != null && !Objects.equals(userDb.getId(), entity.getId())) {
            throw new BookmarkException(ExceptionCode.USERNAME_WAS_USED);
        }
        String password = PasswordUtil.compute(entity.getPassword());
        //重设加密后的密码
        entity.setPassword(password);
        entity.setUpdateTime(DateUtil.now());
        super.saveOrUpdate(entity);
        //同时生成用户设置 UserSetting 的数据库数据
        UserSetting userSetting = new UserSetting();
        userSetting.setUserId(entity.getId());
        userSettingService.save(userSetting);
        return true;
    }

    @Override
    public User getByUsername(String username) {
        LambdaQueryWrapper<User> userLambdaQueryWrapper = new LambdaQueryWrapper<>();
        userLambdaQueryWrapper.eq(User::getUsername, username);
        User user = this.getOne(userLambdaQueryWrapper);
        if (user == null) {
            throw new BookmarkException(ExceptionCode.USER_NOT_EXIST);
        }
        UserRoleType userRoleType = userRoleService.getByUsername(user.getUsername());
        user.setRole(userRoleType.getName());
        return user;
    }

    @Override
    public User getDesensitizedUserByUserName(String username) {
        User user = getByUsername(username);
        user.setPassword(Constant.DESENSITIZED_PASSWORD);
        return user;
    }
}




