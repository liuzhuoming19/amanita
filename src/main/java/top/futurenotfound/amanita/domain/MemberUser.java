package top.futurenotfound.amanita.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * 会员用户
 *
 * @author DK
 */
@TableName(value = "member_user")
@Data
public class MemberUser implements Serializable {
    @Serial
    private static final long serialVersionUID = -8165129208113087472L;
    @TableId(value = "id")
    private String id;
    /**
     * 用户id
     */
    @TableField(value = "user_id")
    private String userId;

    /**
     * 用户名称
     */
    @TableField(value = "user_name")
    private String userName;

    /**
     * 到期时间
     */
    @TableField(value = "expire_time")
    private Date expireTime;

    /**
     * 会员状态
     */
    @ApiModelProperty("是否可用（0禁用 1可用 2过期）")
    @TableField(value = "status")
    private Integer status;

    @TableField(value = "create_time")
    private Date createTime;
    @TableField(value = "update_time")
    private Date updateTime;
}