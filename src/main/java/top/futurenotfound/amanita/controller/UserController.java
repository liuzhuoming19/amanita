package top.futurenotfound.amanita.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import top.futurenotfound.amanita.domain.User;
import top.futurenotfound.amanita.dto.UserPasswordDTO;
import top.futurenotfound.amanita.service.UserService;
import top.futurenotfound.amanita.util.CurrentLoginUser;

/**
 * 用户
 *
 * @author liuzhuoming
 */
@RequestMapping("/user")
@RestController
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<User> get() {
        //非脱敏用户
        User user = CurrentLoginUser.get();
        return ResponseEntity.ok(userService.getDesensitizedUserByUserName(user.getUsername()));
    }

    //TODO 需设置无需权限即可访问，但要考虑和同controller方法的冲突
    @PostMapping
    public ResponseEntity<User> add(User user) {
        return ResponseEntity.ok(userService.save(user));
    }

    @PutMapping
    public ResponseEntity<User> update(UserPasswordDTO userPasswordDTO) {
        return ResponseEntity.ok(userService.updatePassword(userPasswordDTO));
    }
}
