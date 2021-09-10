package top.futurenotfound.bookmark.manager.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
public class TokenEntity {
    @ApiModelProperty("token")
    private String token;
    @ApiModelProperty("token创建时间")
    private Date createTime;
    @ApiModelProperty("token过期时间")
    private Date expireTime;
}
