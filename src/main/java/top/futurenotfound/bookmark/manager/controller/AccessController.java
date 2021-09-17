package top.futurenotfound.bookmark.manager.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
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

import java.util.Objects;

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
    public ResponseEntity<Access> add() {
        User user = CurrentLoginUser.get();
        Access access = accessService.generateAccess(user.getId());
        return ResponseEntity.ok(access);
    }

    @PutMapping
    public ResponseEntity<Access> update(String id) {
        User user = CurrentLoginUser.get();
        Access accessDb = accessService.getDesensitizedById(id);
        if (accessDb == null) throw new AuthException(ExceptionCode.ACCESS_EXPIRED);
        if (Objects.equals(accessDb.getUserId(), user.getId())) throw new AuthException(ExceptionCode.NO_AUTH);
        Access access = accessService.regenerateAccess(id);
        return ResponseEntity.ok(access);
    }

    @GetMapping
    public ResponseEntity<Page<Access>> page(@RequestParam(defaultValue = "10") Integer pageSize,
                                             @RequestParam(defaultValue = "1") Integer pageNum) {
        User user = CurrentLoginUser.get();
        if (pageSize > 100) pageSize = 100;
        return ResponseEntity.ok(accessService.pageDesensitizedByUserId(user.getId(), new Page<>(pageNum, pageSize)));

    }
}
