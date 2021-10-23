package top.futurenotfound.amanita.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import top.futurenotfound.amanita.domain.Bookmark;
import top.futurenotfound.amanita.domain.User;
import top.futurenotfound.amanita.dto.BookmarkDTO;
import top.futurenotfound.amanita.dto.BookmarkSearchDTO;
import top.futurenotfound.amanita.env.Constant;
import top.futurenotfound.amanita.exception.AuthException;
import top.futurenotfound.amanita.exception.BookmarkException;
import top.futurenotfound.amanita.exception.GlobalExceptionCode;
import top.futurenotfound.amanita.helper.RedisPublisher;
import top.futurenotfound.amanita.service.BookmarkService;
import top.futurenotfound.amanita.util.BeanUtil;
import top.futurenotfound.amanita.util.CurrentLoginUser;
import top.futurenotfound.amanita.util.DateUtil;
import top.futurenotfound.amanita.util.StringUtil;

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
    private final RedisPublisher redisPublisher;

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
    public ResponseEntity<Page<Bookmark>> page(@RequestParam(defaultValue = "10") Integer pageSize,
                                               @RequestParam(defaultValue = "1") Integer pageNum,
                                               @Valid BookmarkSearchDTO bookmarkSearchDTO) {
        User user = CurrentLoginUser.get();
        if (pageSize > 100) pageSize = 100;
        return ResponseEntity.ok(bookmarkService.pageByUserIdAndSearchType(user.getId(), bookmarkSearchDTO,
                new Page<>(pageNum, pageSize)));
    }

    @PostMapping
    @ApiOperation("新增")
    public ResponseEntity<Bookmark> add(@RequestBody @Valid BookmarkDTO bookmarkDTO) {
        Bookmark bookmark = BeanUtil.convert(bookmarkDTO, Bookmark.class);
        //发送至队列等待消费
        redisPublisher.sendToMessageQueue(bookmarkDTO);
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

    @DeleteMapping("star/{id}")
    @ApiOperation("取消收藏")
    public ResponseEntity<Bookmark> deleteStar(@PathVariable String id) {
        User user = CurrentLoginUser.get();
        Bookmark bookmark = bookmarkService.getById(id);

        if (bookmark == null) throw new BookmarkException(GlobalExceptionCode.BOOKMARK_NOT_EXIST);
        if (!StringUtil.equals(user.getId(), bookmark.getUserId()))
            throw new AuthException(GlobalExceptionCode.NO_AUTH);

        bookmark.setIsStarred(0);
        bookmarkService.updateById(bookmark);
        return ResponseEntity.ok(bookmark);
    }

    @PostMapping("star/{id}")
    @ApiOperation("添加收藏")
    public ResponseEntity<Bookmark> addStar(@PathVariable String id) {
        User user = CurrentLoginUser.get();
        Bookmark bookmark = bookmarkService.getById(id);

        if (bookmark == null) throw new BookmarkException(GlobalExceptionCode.BOOKMARK_NOT_EXIST);
        if (!StringUtil.equals(user.getId(), bookmark.getUserId()))
            throw new AuthException(GlobalExceptionCode.NO_AUTH);

        bookmark.setIsStarred(1);
        bookmarkService.updateById(bookmark);
        return ResponseEntity.ok(bookmark);
    }

    @DeleteMapping("recycle/{id}")
    @ApiOperation("取消回收站")
    public ResponseEntity<Bookmark> deleteRecycle(@PathVariable String id) {
        User user = CurrentLoginUser.get();
        Bookmark bookmark = bookmarkService.getById(id);

        if (bookmark == null) throw new BookmarkException(GlobalExceptionCode.BOOKMARK_NOT_EXIST);
        if (!StringUtil.equals(user.getId(), bookmark.getUserId()))
            throw new AuthException(GlobalExceptionCode.NO_AUTH);

        bookmark.setIsDeleted(0);
        bookmarkService.updateById(bookmark);
        return ResponseEntity.ok(bookmark);
    }

    @PostMapping("recycle/{id}")
    @ApiOperation("添加回收站")
    public ResponseEntity<Bookmark> addRecycle(@PathVariable String id) {
        User user = CurrentLoginUser.get();
        Bookmark bookmark = bookmarkService.getById(id);

        if (bookmark == null) throw new BookmarkException(GlobalExceptionCode.BOOKMARK_NOT_EXIST);
        if (!StringUtil.equals(user.getId(), bookmark.getUserId()))
            throw new AuthException(GlobalExceptionCode.NO_AUTH);

        bookmark.setIsDeleted(1);
        bookmark.setDeleteTime(DateUtil.now());
        bookmarkService.updateById(bookmark);
        return ResponseEntity.ok(bookmark);
    }
}
