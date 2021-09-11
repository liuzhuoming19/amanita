package top.futurenotfound.bookmark.manager.service;

import com.baomidou.mybatisplus.extension.service.IService;
import top.futurenotfound.bookmark.manager.domain.BookmarkTag;

import java.util.List;

/**
 * 书签标签
 *
 * @author liuzhuoming
 */
public interface BookmarkTagService extends IService<BookmarkTag> {

    List<BookmarkTag> listByBookmarkId(String bookmarkId);
}
