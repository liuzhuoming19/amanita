package top.futurenotfound.bookmark.manager.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import top.futurenotfound.bookmark.manager.domain.*;
import top.futurenotfound.bookmark.manager.env.UserRoleType;
import top.futurenotfound.bookmark.manager.exception.BookmarkException;
import top.futurenotfound.bookmark.manager.exception.ExceptionCode;
import top.futurenotfound.bookmark.manager.helper.ContentExtractorHelper;
import top.futurenotfound.bookmark.manager.mapper.BookmarkMapper;
import top.futurenotfound.bookmark.manager.service.BookmarkService;
import top.futurenotfound.bookmark.manager.service.BookmarkTagService;
import top.futurenotfound.bookmark.manager.service.TagService;
import top.futurenotfound.bookmark.manager.service.UserSettingService;
import top.futurenotfound.bookmark.manager.util.CurrentLoginUser;
import top.futurenotfound.bookmark.manager.util.StringUtil;

import java.util.List;

/**
 * 书签
 *
 * @author liuzhuoming
 */
@Service
@AllArgsConstructor
@Slf4j
public class BookmarkServiceImpl extends ServiceImpl<BookmarkMapper, Bookmark>
        implements BookmarkService {
    private final ContentExtractorHelper contentExtractorHelper;
    private final UserSettingService userSettingService;
    private final TagService tagService;
    private final BookmarkTagService bookmarkTagService;

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

    @Override
    public Bookmark getById(String id) {
        Bookmark bookmark = super.getById(id);
        if (bookmark == null) {
            throw new BookmarkException(ExceptionCode.BOOKMARK_NOT_EXIST);
        }
        List<Tag> tags = tagService.listByBookmarkId(bookmark.getId());
        bookmark.setTags(tags);
        return bookmark;
    }

    @Override
    public List<Bookmark> listByUserId(String userId) {
        LambdaQueryWrapper<Bookmark> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Bookmark::getUserId, userId);
        List<Bookmark> list = this.baseMapper.selectList(queryWrapper);
        for (Bookmark bookmark : list) {
            List<Tag> tags = tagService.listByBookmarkId(bookmark.getId());
            bookmark.setTags(tags);
        }
        return list;
    }
}




