package top.futurenotfound.bookmark.manager.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import top.futurenotfound.bookmark.manager.domain.Authority;
import top.futurenotfound.bookmark.manager.domain.User;
import top.futurenotfound.bookmark.manager.mapper.UserMapper;
import top.futurenotfound.bookmark.manager.service.AuthorityService;
import top.futurenotfound.bookmark.manager.service.UserService;

import java.io.Serializable;
import java.util.List;

/**
 * 用户
 */
@Service
@AllArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService, UserDetailsService {
    private final AuthorityService authorityService;

    @Override
    public User getById(Serializable id) {
        User user = super.getById(id);
        List<Authority> authorityList = authorityService.listByUserId(id);
        user.setAuthorities(authorityList);
        return user;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LambdaQueryWrapper<User> userLambdaQueryWrapper = new LambdaQueryWrapper<>();
        userLambdaQueryWrapper.eq(User::getUsername, username);
        return this.getOne(userLambdaQueryWrapper);
    }
}




