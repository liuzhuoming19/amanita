package top.futurenotfound.bookmark.manager.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import top.futurenotfound.bookmark.manager.domain.Bookmark;
import top.futurenotfound.bookmark.manager.domain.User;
import top.futurenotfound.bookmark.manager.dto.BookmarkDTO;
import top.futurenotfound.bookmark.manager.env.Constant;
import top.futurenotfound.bookmark.manager.exception.AuthException;
import top.futurenotfound.bookmark.manager.exception.BookmarkException;
import top.futurenotfound.bookmark.manager.exception.GlobalExceptionCode;
import top.futurenotfound.bookmark.manager.service.BookmarkService;
import top.futurenotfound.bookmark.manager.service.BookmarkTagService;
import top.futurenotfound.bookmark.manager.service.TagService;
import top.futurenotfound.bookmark.manager.util.CurrentLoginUser;
import top.futurenotfound.bookmark.manager.util.StringUtil;

import javax.validation.Valid;
import java.util.Objects;

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
    private final TagService tagService;
    private final BookmarkTagService bookmarkTagService;

    @GetMapping("{id}")
    @ApiOperation("详情")
    public ResponseEntity<Bookmark> get(@PathVariable String id) {
        User user = CurrentLoginUser.get();
        Bookmark bookmark = bookmarkService.getById(id);

        if (bookmark == null) throw new BookmarkException(GlobalExceptionCode.BOOKMARK_NOT_EXIST);
        if (!StringUtil.equals(user.getId(), bookmark.getUserId()))
            throw new AuthException(GlobalExceptionCode.NO_AUTH);

        return ResponseEntity.ok(bookmark);
    }

    @GetMapping
    @ApiOperation("分页列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "searchType", value = "搜索类型 0普通1加星2已删除")
    })
    public ResponseEntity<Page<Bookmark>> page(@RequestParam(defaultValue = "10") Integer pageSize,
                                               @RequestParam(defaultValue = "1") Integer pageNum,
                                               @RequestParam(defaultValue = "0") Integer searchType) {
        User user = CurrentLoginUser.get();
        if (pageSize > 100) pageSize = 100;
        return ResponseEntity.ok(bookmarkService.pageByUserIdAndSearchType(user.getId(), searchType,
                new Page<>(pageNum, pageSize)));
    }

    @PostMapping
    @ApiOperation("新增")
    public ResponseEntity<Bookmark> add(@RequestBody @Valid BookmarkDTO bookmarkDTO) {
        Bookmark bookmark = bookmarkService.save(bookmarkDTO);
        return ResponseEntity.ok(bookmark);
    }

    @PutMapping
    @ApiOperation("更新")
    public ResponseEntity<Bookmark> update(@RequestBody @Valid BookmarkDTO bookmarkDTO) {
        User user = CurrentLoginUser.get();
        Bookmark bookmarkDb = bookmarkService.getById(bookmarkDTO.getId());

        if (!StringUtil.equals(user.getId(), bookmarkDb.getUserId()))
            throw new AuthException(GlobalExceptionCode.NO_AUTH);

        if (Objects.equals(bookmarkDb.getIsDeleted(), Constant.DATABASE_TRUE))
            throw new BookmarkException(GlobalExceptionCode.BOOKMARK_IS_ALREADY_DELETED);

        Bookmark bookmark = bookmarkService.update(bookmarkDTO);
        return ResponseEntity.ok(bookmark);
    }

    @DeleteMapping("{id}")
    @ApiOperation("删除")
    public ResponseEntity<Bookmark> delete(@PathVariable String id) {
        User user = CurrentLoginUser.get();
        Bookmark bookmark = bookmarkService.getById(id);

        if (bookmark == null) throw new BookmarkException(GlobalExceptionCode.BOOKMARK_NOT_EXIST);
        if (!StringUtil.equals(user.getId(), bookmark.getUserId()))
            throw new AuthException(GlobalExceptionCode.NO_AUTH);

        bookmarkService.deleteById(id);
        return ResponseEntity.ok(bookmark);
    }
}
