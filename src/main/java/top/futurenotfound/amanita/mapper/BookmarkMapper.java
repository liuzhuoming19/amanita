package top.futurenotfound.amanita.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import top.futurenotfound.amanita.domain.Bookmark;
import top.futurenotfound.amanita.dto.BookmarkSearchDTO;

/**
 * 书签
 *
 * @author liuzhuoming
 */
public interface BookmarkMapper extends BaseMapper<Bookmark> {

    Page<Bookmark> pageByTag(Page<Bookmark> page,
                             @Param("userId") String userId,
                             @Param("bookmarkSearchDTO") BookmarkSearchDTO bookmarkSearchDTO);
}




