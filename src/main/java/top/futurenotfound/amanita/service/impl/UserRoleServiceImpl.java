package top.futurenotfound.amanita.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import top.futurenotfound.amanita.domain.Vip;
import top.futurenotfound.amanita.env.UserRoleType;
import top.futurenotfound.amanita.service.UserAdminService;
import top.futurenotfound.amanita.service.UserRoleService;
import top.futurenotfound.amanita.service.VipService;
import top.futurenotfound.amanita.util.DateUtil;

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
