package top.futurenotfound.amanita.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import top.futurenotfound.amanita.domain.User;
import top.futurenotfound.amanita.domain.UserSetting;
import top.futurenotfound.amanita.dto.UserPasswordDTO;
import top.futurenotfound.amanita.env.Constant;
import top.futurenotfound.amanita.env.UserRoleType;
import top.futurenotfound.amanita.exception.AuthException;
import top.futurenotfound.amanita.exception.BookmarkException;
import top.futurenotfound.amanita.exception.GlobalExceptionCode;
import top.futurenotfound.amanita.mapper.UserMapper;
import top.futurenotfound.amanita.service.UserRoleService;
import top.futurenotfound.amanita.service.UserService;
import top.futurenotfound.amanita.service.UserSettingService;
import top.futurenotfound.amanita.util.CurrentLoginUser;
import top.futurenotfound.amanita.util.PasswordUtil;

import java.util.Objects;

/**
 * 用户
 *
 * @author liuzhuoming
 */
@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserMapper userMapper;
    private final UserSettingService userSettingService;
    private final UserRoleService userRoleService;

    @Override
    public User getById(String id) {
        User user = userMapper.selectById(id);
        if (user == null) {
            throw new BookmarkException(GlobalExceptionCode.USER_NOT_EXIST);
        }
        UserRoleType userRoleType = userRoleService.getByUserId(user.getId());
        user.setRole(userRoleType.getName());
        return user;
    }

    @Override
    public User save(User user) {
        User usernameUser = getByUsername(user.getUsername());
        if (usernameUser != null && !Objects.equals(usernameUser.getId(), user.getId())) {
            throw new BookmarkException(GlobalExceptionCode.USERNAME_WAS_USED);
        }
        User emailUser = getByEmail(user.getEmail());
        if (emailUser != null && !Objects.equals(emailUser.getId(), user.getId())) {
            throw new BookmarkException(GlobalExceptionCode.EMAIL_WAS_USED);
        }

        String password = PasswordUtil.compute(user.getPassword());
        //重设加密后的密码
        user.setPassword(password);
        user.setEnabled(0);
        userMapper.insert(user);
        //同时生成用户设置 UserSetting 的数据库数据
        UserSetting userSetting = new UserSetting();
        userSetting.setUserId(user.getId());
        userSettingService.save(userSetting);

        return getDesensitizedUserByUserName(user.getUsername());
    }

    @Override
    public User updatePassword(UserPasswordDTO userPasswordDTO) {
        User user = CurrentLoginUser.get();
        if (!PasswordUtil.verify(userPasswordDTO.getPassword(), user.getPassword()))
            throw new AuthException(GlobalExceptionCode.OLD_PASSWORD_NOT_MATCH);

        User newUser = new User();
        newUser.setId(user.getId());
        newUser.setPassword(PasswordUtil.compute(userPasswordDTO.getNewPassword()));
        userMapper.updateById(newUser);

        return getDesensitizedUserByUserName(user.getUsername());
    }

    @Override
    public User getByUsername(String username) {
        LambdaQueryWrapper<User> userLambdaQueryWrapper = new LambdaQueryWrapper<>();
        userLambdaQueryWrapper.eq(User::getUsername, username);
        User user = userMapper.selectOne(userLambdaQueryWrapper);
        if (user == null) return null;
        UserRoleType userRoleType = userRoleService.getByUserId(user.getId());
        user.setRole(userRoleType.getName());
        return user;
    }

    private User getByEmail(String email) {
        LambdaQueryWrapper<User> userLambdaQueryWrapper = new LambdaQueryWrapper<>();
        userLambdaQueryWrapper.eq(User::getEmail, email);
        User user = userMapper.selectOne(userLambdaQueryWrapper);
        if (user == null) return null;
        UserRoleType userRoleType = userRoleService.getByUserId(user.getId());
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




