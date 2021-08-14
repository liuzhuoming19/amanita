package top.futurenotfound.bookmark.manager.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import top.futurenotfound.bookmark.manager.domain.Authority;
import top.futurenotfound.bookmark.manager.domain.UserAuthority;
import top.futurenotfound.bookmark.manager.mapper.AuthorityMapper;
import top.futurenotfound.bookmark.manager.service.AuthorityService;
import top.futurenotfound.bookmark.manager.service.UserAuthorityService;

import java.io.Serializable;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 权限
 */
@Service
@AllArgsConstructor
public class AuthorityServiceImpl extends ServiceImpl<AuthorityMapper, Authority>
        implements AuthorityService {
    private final UserAuthorityService userAuthorityService;

    @Override
    public List<Authority> listByUserId(Serializable userId) {
        LambdaQueryWrapper<UserAuthority> userAuthorityLambdaQueryWrapper = new LambdaQueryWrapper<>();
        userAuthorityLambdaQueryWrapper.eq(UserAuthority::getUserId, userId);
        List<UserAuthority> userAuthorityList = userAuthorityService.list(userAuthorityLambdaQueryWrapper);
        Set<String> authorityIdList = userAuthorityList.stream()
                .map(UserAuthority::getAuthorityId)
                .collect(Collectors.toSet());
        LambdaQueryWrapper<Authority> authorityLambdaQueryWrapper = new LambdaQueryWrapper<>();
        authorityLambdaQueryWrapper.in(Authority::getId, authorityIdList);
        return this.list(authorityLambdaQueryWrapper);
    }
}




