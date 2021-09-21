package top.futurenotfound.bookmark.manager.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户管理员
 *
 * @author liuzhuoming
 */
@TableName(value = "user_admin")
@Data
public class UserAdmin implements Serializable {
    private static final long serialVersionUID = -8165129208113087422L;
    @TableId(value = "id")
    private String id;
    /**
     * 用户id
     */
    @TableField(value = "user_id")
    private String userId;

    @TableField(value = "create_time")
    private Date createTime;
    @TableField(value = "update_time")
    private Date updateTime;
}