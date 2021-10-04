package top.futurenotfound.amanita.controller;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import top.futurenotfound.amanita.domain.MemberUser;
import top.futurenotfound.amanita.domain.User;
import top.futurenotfound.amanita.dto.MemberUserDTO;
import top.futurenotfound.amanita.env.UserRoleType;
import top.futurenotfound.amanita.exception.AuthException;
import top.futurenotfound.amanita.exception.GlobalExceptionCode;
import top.futurenotfound.amanita.exception.MemberException;
import top.futurenotfound.amanita.service.MemberUserService;
import top.futurenotfound.amanita.util.CurrentLoginUser;

import javax.validation.Valid;

/**
 * 用户
 *
 * @author DK
 */
@RequestMapping("member")
@RestController
@Api(value = "MemberUserController", tags = "会员controller")
@AllArgsConstructor
public class MemberUserController {
    private final MemberUserService memberUserService;

    @GetMapping
    @ApiOperation("分页列表")
    public ResponseEntity<Page<MemberUser>> page(@RequestParam(defaultValue = "10") Integer pageSize,
                                                 @RequestParam(defaultValue = "1") Integer pageNum,
                                                 @Valid MemberUserDTO memberUserDTO) {
        User user = CurrentLoginUser.get();
        if (ObjectUtil.isNull(user) || UserRoleType.ADMIN != UserRoleType.getByName(user.getRole()))
            throw new AuthException(GlobalExceptionCode.NO_AUTH);

        if (pageSize > 100) pageSize = 100;
        return ResponseEntity.ok(memberUserService.findPage(memberUserDTO,
                new Page<>(pageNum, pageSize)));
    }

    @GetMapping("{id}")
    @ApiOperation("查询会员详情")
    public ResponseEntity<MemberUserDTO> page(@PathVariable String id) {
        User user = CurrentLoginUser.get();
        if (ObjectUtil.isNull(user) || UserRoleType.ADMIN != UserRoleType.getByName(user.getRole()))
            throw new AuthException(GlobalExceptionCode.NO_AUTH);

        return ResponseEntity.ok(memberUserService.getMemberInfo(id));
    }

    @DeleteMapping("{id}")
    @ApiOperation("删除会员")
    public ResponseEntity<MemberUser> remove(@PathVariable String id) {
        User user = CurrentLoginUser.get();
        MemberUser memberUser = memberUserService.getById(id);

        if (memberUser == null) throw new MemberException(GlobalExceptionCode.MEMBER_NOT_EXIST);
        if (ObjectUtil.isNull(user))
            throw new AuthException(GlobalExceptionCode.NO_AUTH);

        memberUserService.deleteById(id);
        return ResponseEntity.ok(memberUser);
    }

    @PostMapping
    @ApiOperation("新增会员")
    public ResponseEntity<MemberUserDTO> add(@RequestBody @Valid MemberUserDTO memberUserDTO) {
        User user = CurrentLoginUser.get();
        if (ObjectUtil.isNull(user) || UserRoleType.ADMIN != UserRoleType.getByName(user.getRole()))
            throw new AuthException(GlobalExceptionCode.NO_AUTH);

        memberUserService.save(memberUserDTO);
        return ResponseEntity.ok(memberUserDTO);
    }

    @PutMapping
    @ApiOperation("更新会员")
    public ResponseEntity<MemberUserDTO> modify(@RequestBody @Valid MemberUserDTO memberUserDTO) {
        User user = CurrentLoginUser.get();
        if (ObjectUtil.isNull(user) || UserRoleType.ADMIN != UserRoleType.getByName(user.getRole()))
            throw new AuthException(GlobalExceptionCode.NO_AUTH);

        MemberUser memberUser = memberUserService.getById(memberUserDTO.getId());
        if (memberUser == null) throw new MemberException(GlobalExceptionCode.MEMBER_NOT_EXIST);

        if (ObjectUtil.isNull(user)) throw new AuthException(GlobalExceptionCode.NO_AUTH);

        memberUserService.modify(memberUserDTO);
        return ResponseEntity.ok(memberUserDTO);
    }

}
