package top.futurenotfound.bookmark.manager.service;

import top.futurenotfound.bookmark.manager.domain.Tag;

import java.util.List;

/**
 * 标签
 *
 * @author liuzhuoming
 */
public interface TagService {

    List<Tag> listByBookmarkId(String bookmarkId);
}
