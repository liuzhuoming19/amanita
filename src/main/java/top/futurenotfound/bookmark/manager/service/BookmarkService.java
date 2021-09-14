package top.futurenotfound.bookmark.manager.service;

import top.futurenotfound.bookmark.manager.domain.Bookmark;

import java.util.List;

/**
 * 书签
 *
 * @author liuzhuoming
 */
public interface BookmarkService {

    List<Bookmark> listByUserId(String userId);

    Bookmark getById(String id);

    boolean updateById(Bookmark bookmark);

    boolean save(Bookmark bookmark);
}
