package top.futurenotfound.amanita.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.Date;

/**
 * token实体
 *
 * @author liuzhuoming
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "TokenEntity", description = "token实体")
public class TokenEntity implements Serializable {
    private static final long serialVersionUID = -1770994054516301117L;
    @ApiModelProperty("access_token")
    @NotEmpty
    private String accessToken;
    @ApiModelProperty("refresh_token")
    @NotEmpty
    private String refreshToken;
    @ApiModelProperty("token创建时间")
    private Date createTime;
    @ApiModelProperty("token过期时间")
    private Date expireTime;
}
