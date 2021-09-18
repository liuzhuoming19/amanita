package top.futurenotfound.bookmark.manager.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import top.futurenotfound.bookmark.manager.domain.Access;
import top.futurenotfound.bookmark.manager.domain.User;
import top.futurenotfound.bookmark.manager.exception.AuthException;
import top.futurenotfound.bookmark.manager.exception.ExceptionCode;
import top.futurenotfound.bookmark.manager.service.AccessService;
import top.futurenotfound.bookmark.manager.util.CurrentLoginUser;
import top.futurenotfound.bookmark.manager.util.StringUtil;

/**
 * access
 *
 * @author liuzhuoming
 */
@Controller
@RequestMapping("access")
@Api(value = "AccessController", tags = "access认证controller")
@AllArgsConstructor
public class AccessController {
    private final AccessService accessService;

    @PostMapping
    @ApiOperation("新增")
    public ResponseEntity<Access> add() {
        User user = CurrentLoginUser.get();
        Access access = accessService.generateAccess(user.getId());
        return ResponseEntity.ok(access);
    }

    @PutMapping("{id}")
    @ApiOperation("更新")
    public ResponseEntity<Access> update(@PathVariable String id) {
        User user = CurrentLoginUser.get();
        Access accessDb = accessService.getDesensitizedById(id);
        if (accessDb == null) throw new AuthException(ExceptionCode.ACCESS_EXPIRED);
        if (StringUtil.equals(accessDb.getUserId(), user.getId())) throw new AuthException(ExceptionCode.NO_AUTH);
        Access access = accessService.regenerateAccess(id);
        return ResponseEntity.ok(access);
    }

    @GetMapping
    @ApiOperation("分页列表")
    public ResponseEntity<Page<Access>> page(@RequestParam(defaultValue = "10") Integer pageSize,
                                             @RequestParam(defaultValue = "1") Integer pageNum) {
        User user = CurrentLoginUser.get();
        if (pageSize > 100) pageSize = 100;
        return ResponseEntity.ok(accessService.pageDesensitizedByUserId(user.getId(), new Page<>(pageNum, pageSize)));

    }

    @DeleteMapping("{id}")
    @ApiOperation("删除")
    public ResponseEntity<Access> delete(@PathVariable String id) {
        User user = CurrentLoginUser.get();
        Access access = accessService.getDesensitizedById(id);
        if (!StringUtil.equals(access.getUserId(), user.getId())) throw new AuthException(ExceptionCode.NO_AUTH);
        accessService.deleteById(id);
        return ResponseEntity.ok(access);
    }
}
