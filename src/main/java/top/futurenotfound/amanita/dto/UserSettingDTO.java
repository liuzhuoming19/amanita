package top.futurenotfound.amanita.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户设置入参
 *
 * @author liuzhuoming
 */
@ApiModel(value = "UserSettingDTO", description = "用户设置入参")
@Data
public class UserSettingDTO implements Serializable {
    private static final long serialVersionUID = -8505910008519846972L;
    @ApiModelProperty("是否开启页面简述存档（功能类设置） 0否1是")
    private Integer allowFeatExcerptPageArchive;
    @ApiModelProperty("是否开启页面全文存档（功能类设置） 0否1是")
    private Integer allowFeatFullPageArchive;
    @ApiModelProperty("是否开启书签删除历史（开启后可在回收站保存30天）（功能类设置） 0否1是")
    private Integer allowFeatBookmarkDeletedHistory;

    @ApiModelProperty("是否开启精简版页面（UI类设置）（功能类设置） 0否1是")
    private Integer allowUiPageLite;
}