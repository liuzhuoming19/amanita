package top.futurenotfound.bookmark.manager.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 用户设置入参
 *
 * @author liuzhuoming
 */
@ApiModel(value = "UserSettingDTO", description = "用户设置入参")
@Data
public class UserSettingDTO {
    @ApiModelProperty("是否开启页面简述存档（功能类设置） 0否1是")
    private Integer allowFeatExcerptPageArchive;
    @ApiModelProperty("是否开启页面全文存档（功能类设置） 0否1是")
    private Integer allowFeatFullPageArchive;
    @ApiModelProperty("是否开启书签修改历史（开启后可回溯历史）（功能类设置） 0否1是")
    private Integer allowFeatBookmarkChangeHistory;

    @ApiModelProperty("是否开启精简版页面（UI类设置）（功能类设置） 0否1是")
    private Integer allowUiPageLite;
}