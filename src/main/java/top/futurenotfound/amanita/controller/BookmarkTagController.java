package top.futurenotfound.amanita.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import top.futurenotfound.amanita.domain.Bookmark;
import top.futurenotfound.amanita.domain.BookmarkTag;
import top.futurenotfound.amanita.domain.Tag;
import top.futurenotfound.amanita.domain.User;
import top.futurenotfound.amanita.dto.BookmarkTagDTO;
import top.futurenotfound.amanita.exception.AuthException;
import top.futurenotfound.amanita.exception.BookmarkException;
import top.futurenotfound.amanita.exception.GlobalExceptionCode;
import top.futurenotfound.amanita.service.BookmarkService;
import top.futurenotfound.amanita.service.BookmarkTagService;
import top.futurenotfound.amanita.service.TagService;
import top.futurenotfound.amanita.util.CurrentLoginUser;
import top.futurenotfound.amanita.util.StringUtil;

/**
 * 书签标签
 *
 * @author liuzhuoming
 */
@Controller
@RequestMapping("bookmark/tag")
@Api(value = "BookmarkTagController", tags = "书签标签controller")
@AllArgsConstructor
public class BookmarkTagController {
    private final BookmarkTagService bookmarkTagService;
    private final BookmarkService bookmarkService;
    private final TagService tagService;

    @PostMapping
    @ApiOperation("新增")
    public ResponseEntity<BookmarkTag> add(BookmarkTagDTO bookmarkTagDTO) {
        User user = CurrentLoginUser.get();
        Bookmark bookmark = bookmarkService.getById(bookmarkTagDTO.getBookmarkId());
        if (!StringUtil.equals(bookmark.getUserId(), user.getId()))
            throw new AuthException(GlobalExceptionCode.NO_AUTH);

        Tag tag = tagService.getByName(bookmarkTagDTO.getTagName());
        BookmarkTag bookmarkTag = new BookmarkTag();
        bookmarkTag.setBookmarkId(bookmark.getId());
        bookmarkTag.setTagId(tag.getId());

        BookmarkTag bookmarkTagInsert = bookmarkTagService.save(bookmarkTag);
        return ResponseEntity.ok(bookmarkTagInsert);
    }

    @DeleteMapping("{id}")
    @ApiOperation("删除")
    public ResponseEntity<BookmarkTag> delete(@PathVariable String id) {
        User user = CurrentLoginUser.get();

        BookmarkTag bookmarkTag = bookmarkTagService.getById(id);
        if (bookmarkTag == null) throw new BookmarkException(GlobalExceptionCode.BOOKMARK_TAG_NOT_EXIST);

        Bookmark bookmark = bookmarkService.getById(bookmarkTag.getBookmarkId());
        if (!StringUtil.equals(bookmark.getUserId(), user.getId()))
            throw new AuthException(GlobalExceptionCode.NO_AUTH);

        bookmarkTagService.deleteById(id);
        return ResponseEntity.ok(bookmarkTag);
    }
}
