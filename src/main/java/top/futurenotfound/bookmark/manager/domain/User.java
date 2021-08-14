package top.futurenotfound.bookmark.manager.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;

/**
 * 用户
 *
 * @TableName user
 */
@TableName(value = "user")
@Data
public class User implements UserDetails {
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
    /**
     *
     */
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
    private LocalDateTime createTime;
    /**
     *
     */
    @TableField(value = "update_time")
    private LocalDateTime updateTime;
    /**
     * 权限集合
     */
    @TableField(exist = false)
    private Collection<Authority> authorities;

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
}