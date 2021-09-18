package top.futurenotfound.bookmark.manager.controller;

import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import top.futurenotfound.bookmark.manager.domain.Bookmark;
import top.futurenotfound.bookmark.manager.domain.BookmarkTag;
import top.futurenotfound.bookmark.manager.domain.User;
import top.futurenotfound.bookmark.manager.dto.BookmarkTagDTO;
import top.futurenotfound.bookmark.manager.exception.AuthException;
import top.futurenotfound.bookmark.manager.exception.BookmarkException;
import top.futurenotfound.bookmark.manager.exception.ExceptionCode;
import top.futurenotfound.bookmark.manager.service.BookmarkService;
import top.futurenotfound.bookmark.manager.service.BookmarkTagService;
import top.futurenotfound.bookmark.manager.util.CurrentLoginUser;
import top.futurenotfound.bookmark.manager.util.StringUtil;

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

    @PostMapping
    public ResponseEntity<BookmarkTag> add(BookmarkTagDTO bookmarkTagDTO) {
        User user = CurrentLoginUser.get();
        Bookmark bookmark = bookmarkService.getById(bookmarkTagDTO.getBookmarkId());
        if (!StringUtil.equals(bookmark.getUserId(), user.getId())) throw new AuthException(ExceptionCode.NO_AUTH);

        BookmarkTag bookmarkTag = bookmarkTagService.save(bookmarkTagDTO);
        return ResponseEntity.ok(bookmarkTag);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<BookmarkTag> delete(@PathVariable String id) {
        User user = CurrentLoginUser.get();

        BookmarkTag bookmarkTag = bookmarkTagService.getById(id);
        if (bookmarkTag == null) throw new BookmarkException(ExceptionCode.BOOKMARK_TAG_NOT_EXIST);

        Bookmark bookmark = bookmarkService.getById(bookmarkTag.getBookmarkId());
        if (!StringUtil.equals(bookmark.getUserId(), user.getId())) throw new AuthException(ExceptionCode.NO_AUTH);

        bookmarkTagService.deleteById(id);
        return ResponseEntity.ok(bookmarkTag);
    }
}
