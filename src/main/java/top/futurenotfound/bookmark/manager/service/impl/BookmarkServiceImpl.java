package top.futurenotfound.bookmark.manager.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import top.futurenotfound.bookmark.manager.config.CustomProperties;
import top.futurenotfound.bookmark.manager.domain.*;
import top.futurenotfound.bookmark.manager.dto.BookmarkDTO;
import top.futurenotfound.bookmark.manager.env.UserRoleType;
import top.futurenotfound.bookmark.manager.exception.BookmarkException;
import top.futurenotfound.bookmark.manager.exception.ExceptionCode;
import top.futurenotfound.bookmark.manager.helper.ContentExtractorHelper;
import top.futurenotfound.bookmark.manager.mapper.BookmarkMapper;
import top.futurenotfound.bookmark.manager.service.BookmarkService;
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
public class BookmarkServiceImpl implements BookmarkService {
    private final BookmarkMapper bookmarkMapper;
    private final ContentExtractorHelper contentExtractorHelper;
    private final UserSettingService userSettingService;
    private final TagService tagService;
    private final CustomProperties customProperties;

    @Override
    public void deleteById(String id) {
        bookmarkMapper.deleteById(id);
    }

    @Override
    public void save(Bookmark bookmark) {
        bookmarkMapper.insert(bookmark);
    }

    @Override
    public void updateById(Bookmark bookmark) {
        if (bookmarkMapper.selectById(bookmark.getId()) == null)
            throw new BookmarkException(ExceptionCode.BOOKMARK_NOT_EXIST);

        bookmarkMapper.updateById(bookmark);
    }

    @Override
    public Bookmark getById(String id) {
        Bookmark bookmark = bookmarkMapper.selectById(id);
        if (bookmark == null) {
            throw new BookmarkException(ExceptionCode.BOOKMARK_NOT_EXIST);
        }
        List<Tag> tags = tagService.listByBookmarkId(bookmark.getId());
        bookmark.setTags(tags);
        return bookmark;
    }

    @Override
    public Page<Bookmark> pageByUserId(String userId, Page<Bookmark> page) {
        LambdaQueryWrapper<Bookmark> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Bookmark::getUserId, userId)
                .orderByDesc(Bookmark::getCreateTime);
        Page<Bookmark> bookmarkPage = bookmarkMapper.selectPage(page, queryWrapper);
        for (Bookmark bookmark : bookmarkPage.getRecords()) {
            List<Tag> tags = tagService.listByBookmarkId(bookmark.getId());
            bookmark.setTags(tags);
            bookmark.setUrl(customProperties.getRedirectUrl() + bookmark.getId());
        }
        return bookmarkPage;
    }

    /**
     * 查询该用户书签夹是否已存在该书签
     *
     * @param userId 用户id
     * @param url    书签地址
     * @return true存在 false不存在
     */
    private boolean isExistByUserIdAndUrl(String userId, String url) {
        LambdaQueryWrapper<Bookmark> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Bookmark::getUserId, userId)
                .eq(Bookmark::getUrl, url);
        return bookmarkMapper.selectOne(queryWrapper) != null;
    }

    @Override
    public Bookmark mkBookmark(BookmarkDTO bookmarkDTO) {
        User user = CurrentLoginUser.get();

        String url = bookmarkDTO.getUrl();
        String note = bookmarkDTO.getNote();
        String title = bookmarkDTO.getTitle();

        Bookmark bookmark = new Bookmark();
        bookmark.setId(bookmarkDTO.getId());
        bookmark.setUrl(url);
        bookmark.setTitle(title);
        bookmark.setNote(note);
        bookmark.setUserId(user.getId());

        if (isExistByUserIdAndUrl(user.getId(), url))
            throw new BookmarkException(ExceptionCode.BOOKMARK_IS_ALREADY_EXIST);

        UserSetting userSetting = userSettingService.getByUserId(user.getId());
        WebExcerptInfo webExcerptInfo = contentExtractorHelper.excerpt(url);

        bookmark.setFirstImageUrl(webExcerptInfo.getFirstImageUrl());
        bookmark.setHost(webExcerptInfo.getHost());

        if (userSetting.getAllowExcerptPageArchive() == 1) {
            bookmark.setExcerpt(webExcerptInfo.getExcerpt());
        }
        if (StringUtil.isEmpty(title)) {
            bookmark.setTitle(webExcerptInfo.getTitle());
        }

        //特殊情况下的参数补全
        if (StringUtil.isEmpty(title)) {
            bookmark.setTitle(url);
        }

        if (UserRoleType.VIP.getName().equals(user.getRole())) {
            if (userSetting.getAllowFullPageArchive() == 1) {
                //TODO 全文特殊保存
            }
            if (userSetting.getAllowBookmarkChangeHistory() == 1) {
                //TODO 保存修改历史
            }
        }
        return bookmark;
    }
}




