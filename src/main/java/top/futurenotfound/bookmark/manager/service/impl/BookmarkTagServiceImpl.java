package top.futurenotfound.bookmark.manager.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.futurenotfound.bookmark.manager.domain.BookmarkTag;
import top.futurenotfound.bookmark.manager.mapper.BookmarkTagMapper;
import top.futurenotfound.bookmark.manager.service.BookmarkTagService;

import java.util.List;

/**
 * 书签标签
 *
 * @author liuzhuoming
 */
@Service
public class BookmarkTagServiceImpl extends ServiceImpl<BookmarkTagMapper, BookmarkTag>
        implements BookmarkTagService {

    @Override
    public List<BookmarkTag> listByBookmarkId(String bookmarkId) {
        LambdaQueryWrapper<BookmarkTag> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(BookmarkTag::getBookmarkId, bookmarkId);
        return this.baseMapper.selectList(queryWrapper);
    }
}




