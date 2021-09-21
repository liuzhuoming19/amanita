package top.futurenotfound.bookmark.manager.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import top.futurenotfound.bookmark.manager.domain.Bookmark;
import top.futurenotfound.bookmark.manager.domain.User;
import top.futurenotfound.bookmark.manager.exception.AuthException;
import top.futurenotfound.bookmark.manager.exception.BookmarkException;
import top.futurenotfound.bookmark.manager.exception.GlobalExceptionCode;
import top.futurenotfound.bookmark.manager.service.BookmarkService;
import top.futurenotfound.bookmark.manager.util.CurrentLoginUser;
import top.futurenotfound.bookmark.manager.util.DateUtil;
import top.futurenotfound.bookmark.manager.util.StringUtil;

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

    @GetMapping("{bookmarkId}")
    public String redirect(@PathVariable String bookmarkId) {
        Bookmark bookmark = bookmarkService.getById(bookmarkId);
        User user = CurrentLoginUser.get();
        if (bookmark == null) throw new BookmarkException(GlobalExceptionCode.BOOKMARK_NOT_EXIST);
        if (!StringUtil.equals(bookmark.getUserId(), user.getId()))
            throw new AuthException(GlobalExceptionCode.NO_AUTH);
        if (bookmark.getIsRead() == 0) {
            bookmark.setUpdateTime(DateUtil.now());
            bookmark.setFirstReadTime(DateUtil.now());
            bookmark.setIsRead(1);
            bookmarkService.updateById(bookmark);
        }
        return "redirect:" + bookmark.getUrl();
    }
}
