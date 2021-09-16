package top.futurenotfound.bookmark.manager.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotEmpty;
import java.util.Date;
import java.util.List;

/**
 * 书签
 *
 * @author liuzhuoming
 */
@TableName(value = "bookmark")
@Data
@ApiModel(value = "Bookmark", description = "书签")
public class Bookmark {
    @TableId(value = "id")
    @ApiModelProperty("id")
    private String id;
    /**
     * 书签地址
     */
    @TableField(value = "url")
    @ApiModelProperty("书签url")
    @URL
    @NotEmpty
    private String url;
    /**
     * 书签标题
     */
    @TableField(value = "title")
    @ApiModelProperty("书签标题")
    private String title;
    /**
     * 书签地址域名
     */
    @TableField(value = "host")
    @ApiModelProperty("书签地址域名")
    private String host;
    /**
     * 页面摘要
     */
    @TableField(value = "excerpt")
    @ApiModelProperty("页面摘要")
    private String excerpt;
    /**
     * 是否已读
     */
    @TableField(value = "is_read")
    @ApiModelProperty("是否已读 0否 1是")
    private Integer isRead;
    /**
     * 是否失效链接
     */
    @TableField(value = "is_invalid")
    @ApiModelProperty("是否失效链接 0否 1是")
    private Integer isInvalid;
    /**
     * 数据绑定的用户id
     */
    @TableField(value = "user_id")
    @ApiModelProperty("数据绑定的用户id")
    @NotEmpty
    private String userId;
    /**
     * 书签笔记
     */
    @TableField(value = "note")
    @ApiModelProperty("书签笔记")
    private String note;

    @TableField(value = "create_time")
    private Date createTime;
    @TableField(value = "update_time")
    private Date updateTime;

    @TableField(value = "first_image_url")
    @ApiModelProperty("网页首图")
    private String firstImageUrl;
    @TableField(value = "first_read_time")
    @ApiModelProperty("第一次阅读时间")
    private Date firstReadTime;
    @TableField(value = "is_starred")
    @ApiModelProperty("是否收藏 0否 1是")
    private Integer isStarred;

    @TableField(exist = false)
    @ApiModelProperty("标签组")
    private List<Tag> tags;
}