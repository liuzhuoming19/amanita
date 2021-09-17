package top.futurenotfound.bookmark.manager.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import top.futurenotfound.bookmark.manager.domain.BookmarkTag;
import top.futurenotfound.bookmark.manager.domain.Tag;
import top.futurenotfound.bookmark.manager.mapper.BookmarkTagMapper;
import top.futurenotfound.bookmark.manager.service.BookmarkTagService;

import java.util.List;

/**
 * 书签标签
 *
 * @author liuzhuoming
 */
@Service
@AllArgsConstructor
public class BookmarkTagServiceImpl implements BookmarkTagService {
    private final BookmarkTagMapper bookmarkTagMapper;

    @Override
    public List<BookmarkTag> listByBookmarkId(String bookmarkId) {
        LambdaQueryWrapper<BookmarkTag> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(BookmarkTag::getBookmarkId, bookmarkId);
        return bookmarkTagMapper.selectList(queryWrapper);
    }

    @Override
    public void bindingBookmarkAndTags(String bookmarkId, List<Tag> tags) {
        for (Tag tag : tags) {
            BookmarkTag bookmarkTag = new BookmarkTag();
            bookmarkTag.setBookmarkId(bookmarkId);
            bookmarkTag.setTagId(tag.getId());
            bookmarkTagMapper.insert(bookmarkTag);
        }
    }

    @Override
    public void deleteById(String id) {
        bookmarkTagMapper.deleteById(id);
    }
}




