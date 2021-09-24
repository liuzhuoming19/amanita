package top.futurenotfound.amanita.service;

import top.futurenotfound.amanita.domain.BookmarkTag;
import top.futurenotfound.amanita.domain.Tag;

import java.util.List;
import java.util.Set;

/**
 * 书签标签
 *
 * @author liuzhuoming
 */
public interface BookmarkTagService {

    List<BookmarkTag> listByBookmarkId(String bookmarkId);

    /**
     * 绑定书签和标签组
     *
     * @param bookmarkId 书签id
     * @param tags       标签组
     */
    void bindingBookmarkAndTags(String bookmarkId, List<Tag> tags);

    void deleteById(String id);

    void deleteByIds(Set<String> ids);

    void deleteByBookmarkId(String bookmarkId);

    BookmarkTag save(BookmarkTag bookmarkTag);

    BookmarkTag getById(String id);

    BookmarkTag getByBookmarkIdAndTagId(String bookmarkId, String tagId);
}
