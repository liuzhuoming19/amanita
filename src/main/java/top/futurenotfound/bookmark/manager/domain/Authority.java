package top.futurenotfound.bookmark.manager.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.time.LocalDateTime;

/**
 * 用户权限
 *
 * @TableName user_authority
 */
@TableName(value = "user_authority")
@Data
@ApiModel(value = "Authority", description = "用户权限")
public class Authority implements GrantedAuthority {
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
    @ApiModelProperty("id")
    @TableId(value = "id")
    private String id;
    @ApiModelProperty("权限标识")
    @TableField(value = "authority")
    private String authority;
    /**
     * 权限名称
     */
    @TableField(value = "authority_name")
    private String authorityName;
    /**
     *
     */
    @TableField(value = "create_time")
    private LocalDateTime createTime;
    /**
     *
     */
    @TableField(value = "update_time")
    private LocalDateTime updateTime;
}