package top.futurenotfound.bookmark.manager.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.futurenotfound.bookmark.manager.entity.Bookmark;
import top.futurenotfound.bookmark.manager.mapper.BookmarkMapper;
import top.futurenotfound.bookmark.manager.service.BookmarkService;

/**
 * 书签
 */
@Service
public class BookmarkServiceImpl extends ServiceImpl<BookmarkMapper, Bookmark>
        implements BookmarkService {

    @Override
    public boolean save(Bookmark bookmark) {


        return false;
    }

    @Override
    public boolean updateById(Bookmark bookmark) {
        return false;
    }
}




