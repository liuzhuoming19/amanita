package top.futurenotfound.bookmark.manager.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
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

    @GetMapping("{id}")
    @ApiOperation("详情")
    public ResponseEntity<Bookmark> get(@PathVariable String id) {
        return ResponseEntity.ok(bookmarkService.getById(id));
    }

    @GetMapping
    @ApiOperation("分页列表")
    public ResponseEntity<Page<Bookmark>> page(@RequestParam(defaultValue = "10") Integer pageSize,
                                               @RequestParam(defaultValue = "1") Integer pageNum) {
        if (pageSize > 100) pageSize = 100;
        User user = CurrentLoginUser.get();
        return ResponseEntity.ok(bookmarkService.pageByUserId(user.getId(), new Page<>(pageNum, pageSize)));
    }

    @PostMapping
    @ApiOperation("新增")
    public ResponseEntity<Boolean> add(Bookmark bookmark) {
        return ResponseEntity.ok(bookmarkService.save(bookmark));
    }

    @PutMapping
    @ApiOperation("更新")
    public ResponseEntity<Boolean> update(Bookmark bookmark) {
        return ResponseEntity.ok(bookmarkService.updateById(bookmark));
    }

    @DeleteMapping("{id}")
    @ApiOperation("删除")
    public ResponseEntity<Boolean> delete(@PathVariable String id) {
        return ResponseEntity.ok(bookmarkService.deleteById(id));
    }
}
