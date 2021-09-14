package top.futurenotfound.bookmark.manager.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import top.futurenotfound.bookmark.manager.domain.Bookmark;
import top.futurenotfound.bookmark.manager.domain.User;
import top.futurenotfound.bookmark.manager.exception.AuthException;
import top.futurenotfound.bookmark.manager.exception.BookmarkException;
import top.futurenotfound.bookmark.manager.exception.ExceptionCode;
import top.futurenotfound.bookmark.manager.service.BookmarkService;
import top.futurenotfound.bookmark.manager.util.CurrentLoginUser;
import top.futurenotfound.bookmark.manager.util.DateUtil;

import java.util.Objects;

/**
 * 重定向controller
 *
 * @author liuzhuoming
 */
@Controller
@RequestMapping("redirect")
@AllArgsConstructor
public class RedirectController {
    private final BookmarkService bookmarkService;

    @GetMapping
    public String redirect(String bookmarkId) {
        Bookmark bookmark = bookmarkService.getById(bookmarkId);
        User user = CurrentLoginUser.get();
        if (bookmark == null) throw new BookmarkException(ExceptionCode.BOOKMARK_NOT_EXIST);
        if (!Objects.equals(bookmark.getUserId(), user.getId()))
            throw new AuthException(ExceptionCode.NO_AUTH);
        if (!bookmark.getIsRead()) {
            bookmark.setUpdateTime(DateUtil.now());
            bookmark.setLastReadTime(DateUtil.now());
            bookmark.setIsRead(true);
            bookmarkService.updateById(bookmark);
        }
        return "redirect:" + bookmark.getUrl();
    }
}
