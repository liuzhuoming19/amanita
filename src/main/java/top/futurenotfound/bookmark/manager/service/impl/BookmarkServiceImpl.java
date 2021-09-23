package top.futurenotfound.bookmark.manager.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import top.futurenotfound.bookmark.manager.config.CustomProperties;
import top.futurenotfound.bookmark.manager.domain.*;
import top.futurenotfound.bookmark.manager.dto.BookmarkDTO;
import top.futurenotfound.bookmark.manager.env.BookmarkSearchType;
import top.futurenotfound.bookmark.manager.env.Constant;
import top.futurenotfound.bookmark.manager.env.UserRoleType;
import top.futurenotfound.bookmark.manager.exception.BookmarkException;
import top.futurenotfound.bookmark.manager.exception.GlobalExceptionCode;
import top.futurenotfound.bookmark.manager.helper.ContentExtractorHelper;
import top.futurenotfound.bookmark.manager.mapper.BookmarkMapper;
import top.futurenotfound.bookmark.manager.service.BookmarkService;
import top.futurenotfound.bookmark.manager.service.BookmarkTagService;
import top.futurenotfound.bookmark.manager.service.TagService;
import top.futurenotfound.bookmark.manager.service.UserSettingService;
import top.futurenotfound.bookmark.manager.util.CurrentLoginUser;
import top.futurenotfound.bookmark.manager.util.DateUtil;
import top.futurenotfound.bookmark.manager.util.StringUtil;

import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;

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
    private final BookmarkTagService bookmarkTagService;

    @Override
    public void deleteById(String id) {
        User user = CurrentLoginUser.get();
        UserSetting userSetting = userSettingService.getByUserId(user.getId());
        /*
        vip删除书签可以逻辑删除（伪回收站
        查询回收站书签应设置至今30天内的查询时间
         */
        if ((UserRoleType.VIP.getName().equals(user.getRole()) || UserRoleType.ADMIN.getName().equals(user.getRole()))
                && Objects.equals(userSetting.getAllowFeatBookmarkDeletedHistory(), Constant.DATABASE_TRUE)) {
            Bookmark bookmark = new Bookmark();
            bookmark.setId(id);
            bookmark.setIsDeleted(Constant.DATABASE_TRUE);
            bookmark.setDeleteTime(DateUtil.now());
            bookmarkMapper.updateById(bookmark);
        } else {
            bookmarkMapper.deleteById(id);
        }
    }

    @Override
    public void updateById(Bookmark bookmark) {
        if (bookmarkMapper.selectById(bookmark.getId()) == null)
            throw new BookmarkException(GlobalExceptionCode.BOOKMARK_NOT_EXIST);

        bookmarkMapper.updateById(bookmark);
    }

    @Override
    public Bookmark getById(String id) {
        Bookmark bookmark = bookmarkMapper.selectById(id);
        if (bookmark == null) {
            throw new BookmarkException(GlobalExceptionCode.BOOKMARK_NOT_EXIST);
        }
        if (Objects.equals(bookmark.getIsDeleted(), Constant.DATABASE_TRUE))
            throw new BookmarkException(GlobalExceptionCode.BOOKMARK_IS_ALREADY_DELETED);
        List<Tag> tags = tagService.listByBookmarkId(bookmark.getId());
        bookmark.setTags(tags);
        return bookmark;
    }

    @Override
    public Page<Bookmark> pageByUserIdAndSearchType(String userId, Integer searchType, Page<Bookmark> page) {
        return switch (BookmarkSearchType.getByCode(searchType)) {
            case NORMAL -> {
                LambdaQueryWrapper<Bookmark> queryWrapper = new LambdaQueryWrapper<>();
                queryWrapper.eq(Bookmark::getUserId, userId)
                        .eq(Bookmark::getIsDeleted, Constant.DATABASE_FALSE)
                        .orderByDesc(Bookmark::getCreateTime);
                Page<Bookmark> bookmarkPage = bookmarkMapper.selectPage(page, queryWrapper);
                for (Bookmark bookmark : bookmarkPage.getRecords()) {
                    List<Tag> tags = tagService.listByBookmarkId(bookmark.getId());
                    bookmark.setTags(tags);
                    bookmark.setUrl(customProperties.getRedirectUrl() + bookmark.getId());
                }
                yield bookmarkPage;
            }
            case STAR -> {
                LambdaQueryWrapper<Bookmark> queryWrapper = new LambdaQueryWrapper<>();
                queryWrapper.eq(Bookmark::getUserId, userId)
                        .eq(Bookmark::getIsDeleted, Constant.DATABASE_FALSE)
                        .eq(Bookmark::getIsStarred, Constant.DATABASE_TRUE)
                        .orderByDesc(Bookmark::getCreateTime);
                Page<Bookmark> bookmarkPage = bookmarkMapper.selectPage(page, queryWrapper);
                for (Bookmark bookmark : bookmarkPage.getRecords()) {
                    List<Tag> tags = tagService.listByBookmarkId(bookmark.getId());
                    bookmark.setTags(tags);
                    bookmark.setUrl(customProperties.getRedirectUrl() + bookmark.getId());
                }
                yield bookmarkPage;
            }
            case DELETE -> {
                LambdaQueryWrapper<Bookmark> queryWrapper = new LambdaQueryWrapper<>();
                queryWrapper.eq(Bookmark::getUserId, userId)
                        .eq(Bookmark::getIsDeleted, Constant.DATABASE_TRUE)
                        //仅限30天内已删除数据
                        .apply("to_date({0},'yyyy-mm-dd hh24:mi:ss') <= delete_time",
                                DateUtil.format(DateUtil.add(DateUtil.now(), ChronoUnit.DAYS, -30), DateUtil.DATETIME_FORMATTER))
                        .orderByDesc(Bookmark::getCreateTime);
                Page<Bookmark> bookmarkPage = bookmarkMapper.selectPage(page, queryWrapper);
                for (Bookmark bookmark : bookmarkPage.getRecords()) {
                    List<Tag> tags = tagService.listByBookmarkId(bookmark.getId());
                    bookmark.setTags(tags);
                    bookmark.setUrl(customProperties.getRedirectUrl() + bookmark.getId());
                }
                yield bookmarkPage;
            }
        };
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
    public Bookmark save(BookmarkDTO bookmarkDTO) {
        Bookmark bookmark = mkBookmark(bookmarkDTO);

        bookmarkMapper.insert(bookmark);

        List<String> tagNames = bookmarkDTO.getTagNames();
        if (tagNames != null && !tagNames.isEmpty()) {
            List<Tag> tagList = tagService.mkTags(bookmarkDTO.getTagNames());
            bookmarkTagService.bindingBookmarkAndTags(bookmark.getId(), tagList);
            bookmark.setTags(tagList);
        }
        return bookmark;
    }

    @Override
    public Bookmark update(BookmarkDTO bookmarkDTO) {
        Bookmark bookmark = mkBookmark(bookmarkDTO);

        bookmarkMapper.updateById(bookmark);

        //先删除全部，再插入
        bookmarkTagService.deleteByBookmarkId(bookmarkDTO.getId());
        List<String> tagNames = bookmarkDTO.getTagNames();
        if (tagNames != null && !tagNames.isEmpty()) {
            List<Tag> tagList = tagService.mkTags(bookmarkDTO.getTagNames());
            bookmarkTagService.bindingBookmarkAndTags(bookmarkDTO.getId(), tagList);
            bookmark.setTags(tagList);
        }
        return bookmark;
    }

    private Long count(String userId) {
        LambdaQueryWrapper<Bookmark> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Bookmark::getUserId, userId)
                .eq(Bookmark::getIsDeleted, Constant.DATABASE_FALSE);
        return bookmarkMapper.selectCount(queryWrapper);
    }

    private Bookmark mkBookmark(BookmarkDTO bookmarkDTO) {
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

        if (!UserRoleType.VIP.getName().equals(user.getRole())) {
            //普通用户可收藏书签数量上限
            if (count(user.getId()) >= customProperties.getUserBookmarkNumMax()) {
                throw new BookmarkException(GlobalExceptionCode.USER_HAS_MAX_BOOKMARKS);
            }
        }

        if (isExistByUserIdAndUrl(user.getId(), url))
            throw new BookmarkException(GlobalExceptionCode.BOOKMARK_IS_ALREADY_EXIST);

        UserSetting userSetting = userSettingService.getByUserId(user.getId());
        WebExcerptInfo webExcerptInfo = contentExtractorHelper.excerpt(url);

        bookmark.setFirstImageUrl(webExcerptInfo.getFirstImageUrl());
        bookmark.setHost(webExcerptInfo.getHost());

        if (Objects.equals(userSetting.getAllowFeatExcerptPageArchive(), Constant.DATABASE_TRUE)) {
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
            if (Objects.equals(userSetting.getAllowFeatFullPageArchive(), Constant.DATABASE_TRUE)) {
                //TODO 全文保存
            }
        }
        return bookmark;
    }
}




