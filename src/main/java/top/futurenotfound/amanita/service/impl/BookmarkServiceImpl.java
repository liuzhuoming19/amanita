package top.futurenotfound.amanita.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.futurenotfound.amanita.domain.*;
import top.futurenotfound.amanita.dto.BookmarkDTO;
import top.futurenotfound.amanita.dto.BookmarkSearchDTO;
import top.futurenotfound.amanita.env.BookmarkSearchKeywordType;
import top.futurenotfound.amanita.env.BookmarkSearchType;
import top.futurenotfound.amanita.env.Constant;
import top.futurenotfound.amanita.env.UserRoleType;
import top.futurenotfound.amanita.exception.BookmarkException;
import top.futurenotfound.amanita.exception.GlobalExceptionCode;
import top.futurenotfound.amanita.helper.ContentExtractorHelper;
import top.futurenotfound.amanita.mapper.BookmarkMapper;
import top.futurenotfound.amanita.properties.AmanitaProperties;
import top.futurenotfound.amanita.service.BookmarkService;
import top.futurenotfound.amanita.service.BookmarkTagService;
import top.futurenotfound.amanita.service.TagService;
import top.futurenotfound.amanita.service.UserSettingService;
import top.futurenotfound.amanita.util.CurrentLoginUser;
import top.futurenotfound.amanita.util.DateUtil;
import top.futurenotfound.amanita.util.StringUtil;

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
@Transactional(rollbackFor = Exception.class)
public class BookmarkServiceImpl implements BookmarkService {
    private final BookmarkMapper bookmarkMapper;
    private final ContentExtractorHelper contentExtractorHelper;
    private final UserSettingService userSettingService;
    private final TagService tagService;
    private final AmanitaProperties amanitaProperties;
    private final BookmarkTagService bookmarkTagService;

    @Override
    public void deleteById(String id) {
        User user = CurrentLoginUser.get();
        UserSetting userSetting = userSettingService.getByUserId(user.getId());
        /*
        vip删除书签可以逻辑删除（伪回收站
        查询回收站书签应设置至今30天内的查询时间
         */
        if ((UserRoleType.VIP.getName().equals(user.getRole()) || UserRoleType.ADMIN.getName().equals(user.getRole())) && Objects.equals(userSetting.getAllowFeatBookmarkDeletedHistory(), Constant.DATABASE_TRUE)) {
            Bookmark bookmark = new Bookmark();
            bookmark.setId(id);
            bookmark.setIsDeleted(Constant.DATABASE_TRUE);
            bookmark.setDeleteTime(DateUtil.now());
            bookmark.setUpdateTime(DateUtil.now());
            bookmarkMapper.updateById(bookmark);
        } else {
            bookmarkMapper.deleteById(id);
        }
    }

    @Override
    public void updateById(Bookmark bookmark) {
        if (bookmarkMapper.selectById(bookmark.getId()) == null)
            throw new BookmarkException(GlobalExceptionCode.BOOKMARK_NOT_EXIST);

        bookmark.setUpdateTime(DateUtil.now());
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

    /**
     * tag关键字查询sql需和mybatis plus的修改同步
     *
     * @see BookmarkMapper#pageByTag(Page, String, BookmarkSearchDTO)
     */
    @Override
    public Page<Bookmark> pageByUserIdAndSearchType(String userId, BookmarkSearchDTO bookmarkSearchDTO, Page<Bookmark> page) {
        LambdaQueryWrapper<Bookmark> queryWrapper = new LambdaQueryWrapper<>();
        //基础条件
        queryWrapper.eq(Bookmark::getUserId, userId).orderByDesc(Bookmark::getCreateTime);
        //关键字条件
        if (bookmarkSearchDTO.getKeywordType() != null) {
            BookmarkSearchKeywordType bookmarkSearchKeywordType = BookmarkSearchKeywordType.getByCode(bookmarkSearchDTO.getKeywordType());
            if (BookmarkSearchKeywordType.TAG.equals(bookmarkSearchKeywordType)) {
                //特殊情况，关联查询使用xml查询，mybatis plus的queryWrapper条件不被使用
                Page<Bookmark> bookmarkPage = bookmarkMapper.pageByTag(page, userId, bookmarkSearchDTO);
                for (Bookmark bookmark : bookmarkPage.getRecords()) {
                    List<Tag> tags = tagService.listByBookmarkId(bookmark.getId());
                    bookmark.setTags(tags);
                    bookmark.setUrl(amanitaProperties.getBookmark().getRedirectUrl() + bookmark.getId());
                }
                return bookmarkPage;
            } else if (BookmarkSearchKeywordType.BOOKMARK.equals(bookmarkSearchKeywordType)) {
                //标题/url/笔记/摘录 模糊搜索
                queryWrapper.and(StringUtil.isNotEmpty(bookmarkSearchDTO.getKeyword()), andQueryWrapper -> andQueryWrapper.like(Bookmark::getTitle, bookmarkSearchDTO.getKeyword()).or().like(Bookmark::getUrl, bookmarkSearchDTO.getKeyword()).or().like(Bookmark::getExcerpt, bookmarkSearchDTO.getKeyword()).or().like(Bookmark::getNote, bookmarkSearchDTO.getKeyword()));
            }
        }
        //查询类型条件
        BookmarkSearchType bookmarkSearchType = BookmarkSearchType.getByCode(bookmarkSearchDTO.getSearchType());
        switch (bookmarkSearchType) {
            case NORMAL:
                queryWrapper.eq(Bookmark::getIsDeleted, Constant.DATABASE_FALSE);
                break;
            case STAR:
                queryWrapper.eq(Bookmark::getIsDeleted, Constant.DATABASE_FALSE).eq(Bookmark::getIsStarred, Constant.DATABASE_TRUE);
                break;
            case DELETE:
                queryWrapper.eq(Bookmark::getIsDeleted, Constant.DATABASE_TRUE)
                        //仅限30天内已删除数据
                        .apply("to_date({0},'yyyy-mm-dd hh24:mi:ss') <= delete_time", DateUtil.format(DateUtil.add(DateUtil.now(), ChronoUnit.DAYS, -30), DateUtil.DATETIME_FORMATTER));
                break;
            case NOTE:
                queryWrapper.eq(Bookmark::getIsDeleted, Constant.DATABASE_FALSE).isNotNull(Bookmark::getNote).ne(Bookmark::getNote, "");
                break;
            default:
                //do nothing
                break;
        }
        queryWrapper.orderByDesc(Bookmark::getCreateTime);
        Page<Bookmark> bookmarkPage = bookmarkMapper.selectPage(page, queryWrapper);
        for (Bookmark bookmark : bookmarkPage.getRecords()) {
            List<Tag> tags = tagService.listByBookmarkId(bookmark.getId());
            bookmark.setTags(tags);
            bookmark.setUrl(amanitaProperties.getBookmark().getRedirectUrl() + bookmark.getId());
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
        queryWrapper.eq(Bookmark::getUserId, userId).eq(Bookmark::getUrl, url);
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

        bookmark.setUpdateTime(DateUtil.now());
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
        queryWrapper.eq(Bookmark::getUserId, userId).eq(Bookmark::getIsDeleted, Constant.DATABASE_FALSE);
        return bookmarkMapper.selectCount(queryWrapper);
    }

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
        bookmark.setIsStarred(bookmarkDTO.getIsStarred() == 1 ? 1 : 0);

        //普通用户可收藏书签数量上限
        if (!UserRoleType.VIP.getName().equals(user.getRole()) && count(user.getId()) >= amanitaProperties.getBookmark().getUserNumMax()) {
            throw new BookmarkException(GlobalExceptionCode.USER_HAS_MAX_BOOKMARKS);
        }

        if (isExistByUserIdAndUrl(user.getId(), url))
            throw new BookmarkException(GlobalExceptionCode.BOOKMARK_IS_ALREADY_EXIST);

        UserSetting userSetting = userSettingService.getByUserId(user.getId());
        WebExcerptInfo webExcerptInfo = ContentExtractorHelper.excerpt(url);

        bookmark.setFirstImageUrl(webExcerptInfo.getFirstImageUrl());
        bookmark.setHost(webExcerptInfo.getHost());

        if (Objects.equals(userSetting.getAllowFeatExcerptPageArchive(), Constant.DATABASE_TRUE)) {
            bookmark.setExcerpt(webExcerptInfo.getExcerpt());
        }
        if (StringUtil.isEmpty(title)) {
            bookmark.setTitle(webExcerptInfo.getTitle());
        }

        //特殊情况下的标题补全为url
        if (StringUtil.isEmpty(bookmark.getTitle())) {
            bookmark.setTitle(url);
        }

        if (UserRoleType.VIP.getName().equals(user.getRole()) && Objects.equals(userSetting.getAllowFeatFullPageArchive(), Constant.DATABASE_TRUE)) {
            //TODO 全文保存
        }
        return bookmark;
    }
}




