package top.futurenotfound.bookmark.manager.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 用户
 * <p>
 * user为postgresql关键字，做表名使用需使用双引号括起来
 *
 * @TableName user
 */
@TableName(value = "\"user\"")
@Data
public class User {
    @TableId(value = "id")
    private String id;
    /**
     * 用户名
     */
    @TableField(value = "username")
    private String username;
    /**
     * 邮箱地址
     */
    @TableField(value = "email")
    private String email;
    /**
     * 密码（加密后）
     */
    @TableField(value = "password")
    private String password;
    /**
     * 是否可用（0不可用 1可用）
     */
    @TableField(value = "enabled")
    private boolean enabled;
    /**
     *
     */
    @TableField(value = "create_time")
    private Date createTime;
    /**
     *
     */
    @TableField(value = "update_time")
    private Date updateTime;

    /**
     * 角色标识
     */
    @TableField(exist = false)
    private String role = "USER";
}