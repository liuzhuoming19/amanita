package top.futurenotfound.bookmark.manager.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import top.futurenotfound.bookmark.manager.domain.BookmarkTag;
import top.futurenotfound.bookmark.manager.domain.Tag;
import top.futurenotfound.bookmark.manager.env.RedisKey;
import top.futurenotfound.bookmark.manager.helper.RedisHelper;
import top.futurenotfound.bookmark.manager.mapper.TagMapper;
import top.futurenotfound.bookmark.manager.service.BookmarkTagService;
import top.futurenotfound.bookmark.manager.service.TagService;
import top.futurenotfound.bookmark.manager.util.RandomColorCodeUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 标签
 *
 * @author liuzhuoming
 */
@Service
@AllArgsConstructor
@Slf4j
public class TagServiceImpl implements TagService {
    private final BookmarkTagService bookmarkTagService;
    private final TagMapper tagMapper;
    private final RedisHelper<Tag> tagRedisHelper;

    @Override
    public List<Tag> listByBookmarkId(String bookmarkId) {
        List<BookmarkTag> bookmarkTags = bookmarkTagService.listByBookmarkId(bookmarkId);
        List<String> tagIds = bookmarkTags.stream()
                .map(BookmarkTag::getTagId)
                .distinct()
                .collect(Collectors.toList());
        if (tagIds.isEmpty()) return Collections.emptyList();
        return tagMapper.selectBatchIds(tagIds);
    }

    @Override
    public List<Tag> mkTags(List<String> tagNames) {
        List<Tag> tags = new ArrayList<>();
        for (String tagName : tagNames) tags.add(getByName(tagName));
        return tags;
    }

    @Override
    public synchronized Tag getByName(String name) {
        String tagKey = RedisKey.TAG + name;
        Tag tag = tagRedisHelper.get(tagKey);
        if (tag == null) {
            LambdaQueryWrapper<Tag> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(Tag::getName, name);
            Tag tagDb = tagMapper.selectOne(queryWrapper);
            if (tagDb != null) {
                tag = tagDb;
            } else {
                tag = new Tag();
                tag.setName(name);
                tag.setColor(RandomColorCodeUtil.next());
                tagMapper.insert(tag);
            }
            tagRedisHelper.setEx(tagKey, tag, 30L, TimeUnit.MINUTES);
        }
        return tag;
    }
}




