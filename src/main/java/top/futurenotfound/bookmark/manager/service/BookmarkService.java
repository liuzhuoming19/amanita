package top.futurenotfound.bookmark.manager.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import top.futurenotfound.bookmark.manager.domain.Bookmark;
import top.futurenotfound.bookmark.manager.dto.BookmarkDTO;

/**
 * 书签
 *
 * @author liuzhuoming
 */
public interface BookmarkService {

    Page<Bookmark> pageByUserIdAndSearchType(String userId, Integer searchType, Page<Bookmark> page);

    Bookmark getById(String id);

    void updateById(Bookmark bookmark);

    void deleteById(String id);

    /**
     * 通过dto构建书签
     *
     * @param bookmarkDTO dto
     */
    Bookmark save(BookmarkDTO bookmarkDTO);

    Bookmark update(BookmarkDTO bookmarkDTO);
}
