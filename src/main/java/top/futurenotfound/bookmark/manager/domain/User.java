package top.futurenotfound.bookmark.manager.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import top.futurenotfound.bookmark.manager.env.UserRoleType;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
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
    private String username;
    /**
     * 邮箱地址
     */
    @TableField(value = "email")
    @ApiModelProperty("邮箱地址")
    @Email
    private String email;
    /**
     * 密码（加密后）
     */
    @TableField(value = "password")
    @ApiModelProperty("密码")
    @NotEmpty
    private String password;
    /**
     * 是否可用（0不可用 1可用）
     */
    @TableField(value = "enabled")
    @ApiModelProperty("是否可用（0不可用 1可用）")
    private boolean enabled;

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