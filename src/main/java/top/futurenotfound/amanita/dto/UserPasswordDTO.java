package top.futurenotfound.amanita.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * 用户密码DTO
 *
 * @author liuzhuoming
 */
@Data
public class UserPasswordDTO {
    @NotEmpty
    private String password;
    @NotEmpty
    private String newPassword;
}
