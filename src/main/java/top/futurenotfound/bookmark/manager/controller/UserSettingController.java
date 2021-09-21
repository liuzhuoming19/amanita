package top.futurenotfound.bookmark.manager.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import top.futurenotfound.bookmark.manager.domain.User;
import top.futurenotfound.bookmark.manager.domain.UserSetting;
import top.futurenotfound.bookmark.manager.dto.UserSettingDTO;
import top.futurenotfound.bookmark.manager.env.UserRoleType;
import top.futurenotfound.bookmark.manager.exception.AuthException;
import top.futurenotfound.bookmark.manager.exception.GlobalExceptionCode;
import top.futurenotfound.bookmark.manager.service.UserSettingService;
import top.futurenotfound.bookmark.manager.util.BeanUtil;
import top.futurenotfound.bookmark.manager.util.CurrentLoginUser;
import top.futurenotfound.bookmark.manager.util.DateUtil;
import top.futurenotfound.bookmark.manager.util.StringUtil;

/**
 * 用户设置
 *
 * @author liuzhuoming
 */
@Controller
@RequestMapping("user/setting")
@Api(value = "UserSettingController", tags = "用户设置controller")
@AllArgsConstructor
public class UserSettingController {
    private final UserSettingService userSettingService;

    @PostMapping
    @ApiOperation("详情")
    public ResponseEntity<UserSetting> get() {
        User user = CurrentLoginUser.get();
        UserSetting userSetting = userSettingService.getByUserId(user.getId());
        return ResponseEntity.ok(userSetting);
    }

    @PutMapping
    @ApiOperation("更新")
    public ResponseEntity<UserSetting> update(UserSettingDTO userSettingDTO) {
        User user = CurrentLoginUser.get();
        UserSetting userSettingDb = userSettingService.getByUserId(user.getId());
        if (!StringUtil.equals(user.getId(), userSettingDb.getUserId()))
            throw new AuthException(GlobalExceptionCode.NO_AUTH);

        //普通用户没有修改全文存档和历史回溯选项的权限
        if (StringUtil.equals(user.getRole(), UserRoleType.USER.getName())) {
            if (userSettingDTO.getAllowFeatBookmarkChangeHistory() == 1
                    || userSettingDTO.getAllowFeatFullPageArchive() == 1)
                throw new AuthException(GlobalExceptionCode.NO_AUTH);
        }

        UserSetting userSetting = BeanUtil.convert(userSettingDb, UserSetting.class);
        userSetting.setAllowFeatBookmarkChangeHistory(userSettingDTO.getAllowFeatBookmarkChangeHistory());
        userSetting.setAllowFeatFullPageArchive(userSettingDTO.getAllowFeatFullPageArchive());
        userSetting.setAllowFeatExcerptPageArchive(userSettingDTO.getAllowFeatExcerptPageArchive());
        userSetting.setAllowUiPageLite(userSettingDTO.getAllowUiPageLite());
        userSetting.setUpdateTime(DateUtil.now());
        UserSetting userSettingDbNow = userSettingService.updateById(userSetting);
        return ResponseEntity.ok(userSettingDbNow);
    }
}
