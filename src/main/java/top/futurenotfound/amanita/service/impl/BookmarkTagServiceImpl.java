package top.futurenotfound.amanita.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import top.futurenotfound.amanita.exception.GlobalExceptionCode;
import top.futurenotfound.amanita.domain.BookmarkTag;
import top.futurenotfound.amanita.domain.Tag;
import top.futurenotfound.amanita.exception.BookmarkException;
import top.futurenotfound.amanita.mapper.BookmarkTagMapper;
import top.futurenotfound.amanita.service.BookmarkTagService;

import java.util.List;
import java.util.Set;

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

    @Override
    public void deleteByIds(Set<String> ids) {
        bookmarkTagMapper.deleteBatchIds(ids);
    }

    @Override
    public void deleteByBookmarkId(String bookmarkId) {
        LambdaQueryWrapper<BookmarkTag> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(BookmarkTag::getBookmarkId, bookmarkId);
        bookmarkTagMapper.delete(queryWrapper);
    }

    @Override
    public BookmarkTag save(BookmarkTag bookmarkTag) {
        BookmarkTag bookmarkTagDb = getByBookmarkIdAndTagId(bookmarkTag.getBookmarkId(), bookmarkTag.getTagId());
        if (bookmarkTagDb != null) throw new BookmarkException(GlobalExceptionCode.BOOKMARK_TAG_IS_ALREADY_EXIST);

        bookmarkTagMapper.insert(bookmarkTag);
        return getById(bookmarkTag.getId());
    }

    @Override
    public BookmarkTag getById(String id) {
        return bookmarkTagMapper.selectById(id);
    }

    @Override
    public BookmarkTag getByBookmarkIdAndTagId(String bookmarkId, String tagId) {
        LambdaQueryWrapper<BookmarkTag> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(BookmarkTag::getBookmarkId, bookmarkId)
                .eq(BookmarkTag::getTagId, tagId);
        return bookmarkTagMapper.selectOne(queryWrapper);
    }
}




