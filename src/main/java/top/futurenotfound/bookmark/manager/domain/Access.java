package top.futurenotfound.bookmark.manager.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * 访问密钥
 * <p>
 * 在非接口认证情况下访问需认证接口
 *
 * @author liuzhuoming
 */
@TableName(value = "access")
@Data
@ApiModel(value = "Access", description = "访问密钥")
public class Access implements Serializable {
    @Serial
    private static final long serialVersionUID = 891419573667527429L;
    @TableId(value = "id")
    @ApiModelProperty("id")
    private String id;
    @TableField(value = "key")
    @ApiModelProperty("access key")
    @NotEmpty
    private String key;
    @TableField(value = "secret")
    @ApiModelProperty("access secret")
    private String secret;
    /**
     * 数据绑定的用户id
     */
    @TableField(value = "user_id")
    @ApiModelProperty("数据绑定的用户id")
    @NotEmpty
    private String userId;
    /**
     * 认证过期时间
     */
    @TableField(value = "expire_time")
    @ApiModelProperty("认证过期时间")
    private Date expireTime;

    @TableField(value = "create_time")
    private Date createTime;
    @TableField(value = "update_time")
    private Date updateTime;
}