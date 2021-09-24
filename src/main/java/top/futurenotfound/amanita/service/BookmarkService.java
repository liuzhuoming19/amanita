package top.futurenotfound.amanita.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import top.futurenotfound.amanita.domain.Bookmark;
import top.futurenotfound.amanita.dto.BookmarkDTO;
import top.futurenotfound.amanita.dto.BookmarkSearchDTO;

/**
 * 书签
 *
 * @author liuzhuoming
 */
public interface BookmarkService {

    Page<Bookmark> pageByUserIdAndSearchType(String userId, BookmarkSearchDTO bookmarkSearchDTO, Page<Bookmark> page);

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
