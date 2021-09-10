package top.futurenotfound.bookmark.manager.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import top.futurenotfound.bookmark.manager.domain.Bookmark;
import top.futurenotfound.bookmark.manager.domain.User;
import top.futurenotfound.bookmark.manager.domain.UserSetting;
import top.futurenotfound.bookmark.manager.domain.WebExcerptInfo;
import top.futurenotfound.bookmark.manager.env.UserRoleType;
import top.futurenotfound.bookmark.manager.helper.ContentExtractorHelper;
import top.futurenotfound.bookmark.manager.mapper.BookmarkMapper;
import top.futurenotfound.bookmark.manager.service.BookmarkService;
import top.futurenotfound.bookmark.manager.service.UserSettingService;
import top.futurenotfound.bookmark.manager.util.CurrentLoginUser;
import top.futurenotfound.bookmark.manager.util.StringUtil;

/**
 * 书签
 */
@Service
@AllArgsConstructor
@Slf4j
public class BookmarkServiceImpl extends ServiceImpl<BookmarkMapper, Bookmark>
        implements BookmarkService {
    private final ContentExtractorHelper contentExtractorHelper;
    private final UserSettingService userSettingService;

    @Override
    public boolean save(Bookmark bookmark) {
        String url = bookmark.getUrl();
        User user = CurrentLoginUser.get();
        UserSetting userSetting = userSettingService.getByUserId(user.getId());
        WebExcerptInfo webExcerptInfo = contentExtractorHelper.excerpt(url);

        bookmark.setFirstImageUrl(webExcerptInfo.getFirstImageUrl());
        bookmark.setHost(webExcerptInfo.getHost());

        if (userSetting.getAllowExcerptPageArchive()) {
            bookmark.setExcerpt(webExcerptInfo.getExcerpt());
        }
        if (StringUtil.isEmpty(bookmark.getTitle())) {
            bookmark.setTitle(webExcerptInfo.getTitle());
        }

        if (UserRoleType.VIP.getName().equals(user.getRole())) {
            if (userSetting.getAllowFullPageArchive()) {
                //TODO 全文特殊保存
            }
            if (userSetting.getAllowBookmarkChangeHistory()) {
                //TODO 保存修改历史
            }
        }
        return this.baseMapper.insert(bookmark) == 1;
    }

    @Override
    public boolean updateById(Bookmark bookmark) {
        String url = bookmark.getUrl();
        User user = CurrentLoginUser.get();
        UserSetting userSetting = userSettingService.getByUserId(user.getId());
        WebExcerptInfo webExcerptInfo = contentExtractorHelper.excerpt(url);

        bookmark.setFirstImageUrl(webExcerptInfo.getFirstImageUrl());
        bookmark.setHost(webExcerptInfo.getHost());

        if (userSetting.getAllowExcerptPageArchive()) {
            bookmark.setExcerpt(webExcerptInfo.getExcerpt());
        }
        if (StringUtil.isEmpty(bookmark.getTitle())) {
            bookmark.setTitle(webExcerptInfo.getTitle());
        }

        if (UserRoleType.VIP.getName().equals(user.getRole())) {
            if (userSetting.getAllowFullPageArchive()) {
                //TODO 全文特殊保存
            }
            if (userSetting.getAllowBookmarkChangeHistory()) {
                //TODO 保存修改历史
            }
        }
        return this.baseMapper.updateById(bookmark) == 1;
    }
}




