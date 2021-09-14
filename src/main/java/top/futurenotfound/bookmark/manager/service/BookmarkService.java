package top.futurenotfound.bookmark.manager.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import top.futurenotfound.bookmark.manager.domain.Bookmark;

/**
 * 书签
 *
 * @author liuzhuoming
 */
public interface BookmarkService {

    Page<Bookmark> pageByUserId(String userId, Page<Bookmark> page);

    Bookmark getById(String id);

    boolean updateById(Bookmark bookmark);

    boolean save(Bookmark bookmark);
}
