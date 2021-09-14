package top.futurenotfound.bookmark.manager.service;

import top.futurenotfound.bookmark.manager.domain.BookmarkTag;

import java.util.List;

/**
 * 书签标签
 *
 * @author liuzhuoming
 */
public interface BookmarkTagService {

    List<BookmarkTag> listByBookmarkId(String bookmarkId);
}
