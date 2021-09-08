package top.futurenotfound.bookmark.manager.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import top.futurenotfound.bookmark.manager.entity.User;
import top.futurenotfound.bookmark.manager.exception.BookmarkException;
import top.futurenotfound.bookmark.manager.exception.ExceptionCode;
import top.futurenotfound.bookmark.manager.mapper.UserMapper;
import top.futurenotfound.bookmark.manager.service.UserService;
import top.futurenotfound.bookmark.manager.util.PasswordUtil;

import java.io.Serializable;
import java.util.Objects;

/**
 * 用户
 */
@Service
@AllArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService {

    @Override
    public User getById(Serializable id) {
        User user = super.getById(id);
        //TODO 添加是否vip查询
        return user;
    }

    @Override
    public User getOne(Wrapper<User> queryWrapper, boolean throwEx) {
        User user = super.getOne(queryWrapper, throwEx);
        //TODO 添加是否vip查询
        return user;
    }

    @Override
    public boolean saveOrUpdate(User entity) {
        User user = getByUsername(entity.getUsername());
        if (user != null && !Objects.equals(user.getId(), entity.getId())) {
            throw new BookmarkException(ExceptionCode.USERNAME_WAS_USED);
        }
        String password = PasswordUtil.compute(entity.getPassword(), entity.getUsername());
        //重设加密后的密码
        entity.setPassword(password);
        return super.saveOrUpdate(entity);
    }

    @Override
    public User getByUsername(String username) {
        LambdaQueryWrapper<User> userLambdaQueryWrapper = new LambdaQueryWrapper<>();
        userLambdaQueryWrapper.eq(User::getUsername, username);
        return this.getOne(userLambdaQueryWrapper);
    }
}




