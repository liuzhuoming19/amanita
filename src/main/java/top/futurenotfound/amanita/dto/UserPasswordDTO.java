package top.futurenotfound.amanita.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serial;
import java.io.Serializable;

/**
 * 用户密码DTO
 *
 * @author liuzhuoming
 */
@Data
public class UserPasswordDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1382529734984683447L;
    @NotEmpty
    private String password;
    @NotEmpty
    private String newPassword;
}
