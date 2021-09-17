package top.futurenotfound.bookmark.manager.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import top.futurenotfound.bookmark.manager.domain.Vip;
import top.futurenotfound.bookmark.manager.env.UserRoleType;
import top.futurenotfound.bookmark.manager.service.UserAdminService;
import top.futurenotfound.bookmark.manager.service.UserRoleService;
import top.futurenotfound.bookmark.manager.service.VipService;
import top.futurenotfound.bookmark.manager.util.DateUtil;

import java.util.Date;
import java.util.List;

/**
 * 用户角色
 *
 * @author liuzhuoming
 */
@Service
@AllArgsConstructor
@Slf4j
public class UserRoleServiceImpl implements UserRoleService {
    private final UserAdminService userAdminService;
    private final VipService vipService;

    @Override
    public UserRoleType getByUserId(String userId) {
        if (userAdminService.getByUserId(userId) != null) {
            return UserRoleType.ADMIN;
        }
        List<Vip> list = vipService.listByUserId(userId);
        Date now = DateUtil.now();
        for (Vip vip : list) {
            if (now.after(vip.getStartTime())
                    && now.before(vip.getEndTime())) {
                return UserRoleType.VIP;
            }
        }
        return UserRoleType.USER;
    }
}
