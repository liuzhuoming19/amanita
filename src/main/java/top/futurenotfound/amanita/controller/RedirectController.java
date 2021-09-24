package top.futurenotfound.amanita.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import top.futurenotfound.amanita.domain.Bookmark;
import top.futurenotfound.amanita.domain.User;
import top.futurenotfound.amanita.exception.AuthException;
import top.futurenotfound.amanita.exception.BookmarkException;
import top.futurenotfound.amanita.exception.GlobalExceptionCode;
import top.futurenotfound.amanita.service.BookmarkService;
import top.futurenotfound.amanita.util.CurrentLoginUser;
import top.futurenotfound.amanita.util.DateUtil;
import top.futurenotfound.amanita.util.StringUtil;

/**
 * 重定向controller
 *
 * @author liuzhuoming
 */
@Controller
@RequestMapping("redirect")
@AllArgsConstructor
@Api(value = "RedirectController", tags = "重定向Controller")
public class RedirectController {
    private final BookmarkService bookmarkService;

    @GetMapping("{bookmarkId}")
    @ApiOperation("重定向")
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
