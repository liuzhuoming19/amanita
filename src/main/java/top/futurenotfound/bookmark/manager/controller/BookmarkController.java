package top.futurenotfound.bookmark.manager.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import top.futurenotfound.bookmark.manager.domain.Bookmark;
import top.futurenotfound.bookmark.manager.domain.User;
import top.futurenotfound.bookmark.manager.service.BookmarkService;
import top.futurenotfound.bookmark.manager.util.CurrentLoginUser;

/**
 * 书签controller
 *
 * @author liuzhuoming
 */
@Controller
@RequestMapping("bookmark")
@Api(value = "BookmarkController", tags = "书签controller")
@AllArgsConstructor
public class BookmarkController {
    private final BookmarkService bookmarkService;

    @PostMapping
    @ApiOperation("新增")
    public ResponseEntity<Boolean> add(Bookmark bookmark) {
        return ResponseEntity.ok(bookmarkService.save(bookmark));
    }

    @GetMapping
    @ApiOperation("分页列表")
    public ResponseEntity<Page<Bookmark>> add(@RequestParam(defaultValue = "10") Integer pageSize,
                                              @RequestParam(defaultValue = "1") Integer pageNum) {
        if (pageSize > 100) pageSize = 100;
        User user = CurrentLoginUser.get();
        return ResponseEntity.ok(bookmarkService.pageByUserId(user.getId(), new Page<>(pageNum, pageSize)));
    }

}
