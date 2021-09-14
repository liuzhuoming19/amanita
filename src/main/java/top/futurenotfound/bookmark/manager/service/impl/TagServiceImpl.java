package top.futurenotfound.bookmark.manager.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import top.futurenotfound.bookmark.manager.domain.BookmarkTag;
import top.futurenotfound.bookmark.manager.domain.Tag;
import top.futurenotfound.bookmark.manager.mapper.TagMapper;
import top.futurenotfound.bookmark.manager.service.BookmarkTagService;
import top.futurenotfound.bookmark.manager.service.TagService;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 标签
 *
 * @author liuzhuoming
 */
@Service
@AllArgsConstructor
@Slf4j
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag>
        implements TagService {
    private final BookmarkTagService bookmarkTagService;

    @Override
    public List<Tag> listByBookmarkId(String bookmarkId) {
        List<BookmarkTag> bookmarkTags = bookmarkTagService.listByBookmarkId(bookmarkId);
        List<String> tagIds = bookmarkTags.stream()
                .map(BookmarkTag::getTagId)
                .distinct()
                .collect(Collectors.toList());
        if (tagIds.isEmpty()) return Collections.emptyList();
        return this.listByIds(tagIds);
    }
}




