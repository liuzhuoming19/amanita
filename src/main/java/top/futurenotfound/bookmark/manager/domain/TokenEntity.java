package top.futurenotfound.bookmark.manager.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * token返回值
 *
 * @author liuzhuoming
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "TokenEntity", description = "token返回值")
public class TokenEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = -1770994054516301117L;
    @ApiModelProperty("access_token")
    private String access_token;
    @ApiModelProperty("refresh_token")
    private String refresh_token;
    @ApiModelProperty("token创建时间")
    private Date createTime;
    @ApiModelProperty("token过期时间")
    private Date expireTime;
}
