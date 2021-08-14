package top.futurenotfound.bookmark.manager.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.futurenotfound.bookmark.manager.domain.UserAuthority;
import top.futurenotfound.bookmark.manager.mapper.UserAuthorityMapper;
import top.futurenotfound.bookmark.manager.service.UserAuthorityService;

/**
 * 用户权限关联
 */
@Service
public class UserAuthorityServiceImpl extends ServiceImpl<UserAuthorityMapper, UserAuthority>
        implements UserAuthorityService {

}




