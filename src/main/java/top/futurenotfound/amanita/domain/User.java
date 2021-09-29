package top.futurenotfound.amanita.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Range;
import top.futurenotfound.amanita.annotation.Sensitive;
import top.futurenotfound.amanita.env.UserRoleType;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * 用户
 * <p>
 * user 为 postgresql 关键字，做表名使用需使用双引号括起来
 *
 * @author liuzhuoming
 */
@TableName(value = "\"user\"")
@Data
@ApiModel(value = "User", description = "用户")
public class User implements Serializable {
    @Serial
    private static final long serialVersionUID = -5597402404889688001L;
    @TableId(value = "id")
    @ApiModelProperty("id")
    private String id;
    /**
     * 用户名
     */
    @TableField(value = "username")
    @ApiModelProperty("用户名")
    @NotEmpty
    @Range(min = 6, max = 12)
    private String username;
    /**
     * 邮箱地址
     */
    @TableField(value = "email")
    @ApiModelProperty("邮箱地址")
    @Email
    @NotEmpty
    private String email;
    /**
     * 密码
     */
    @TableField(value = "password")
    @ApiModelProperty("密码")
    @NotEmpty
    @Range(min = 8, max = 16)
    @Sensitive
    private String password;
    /**
     * 是否可用（0不可用 1可用）
     */
    @TableField(value = "enabled")
    @ApiModelProperty("是否可用（0不可用 1可用）")
    private Integer enabled;

    @TableField(value = "create_time")
    private Date createTime;
    @TableField(value = "update_time")
    private Date updateTime;

    /**
     * 角色标识
     *
     * @see UserRoleType
     */
    @TableField(exist = false)
    @ApiModelProperty("角色标识")
    private String role = UserRoleType.USER.getName();
}