package top.futurenotfound.amanita.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@ApiModel(value = "MemberUserDTO", description = "会员入参")
public class MemberUserDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = -4350404879013155885L;
    @ApiModelProperty(value = "会员id")
    private String id;
    @ApiModelProperty(value = "用户id")
    private String userId;
    @ApiModelProperty(value = "是否为会员,0不是1是2过期")
    private Integer status;
    @ApiModelProperty(value = "用户名称")
    private String userName;
    @ApiModelProperty(value = "会员到期时间")
    private Date expireTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
    }
}
