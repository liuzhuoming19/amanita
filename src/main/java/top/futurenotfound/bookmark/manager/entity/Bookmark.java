package top.futurenotfound.bookmark.manager.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 书签
 *
 * @TableName bookmark
 */
@TableName(value = "bookmark")
@Data
@ApiModel(value = "Bookmark", description = "书签")
public class Bookmark {
    /**
     * id
     */
    @TableId(value = "id")
    @ApiModelProperty("id")
    private String id;
    /**
     * 书签地址
     */
    @TableField(value = "url")
    @ApiModelProperty("书签url")
    private String url;
    /**
     * 书签标题
     */
    @TableField(value = "title")
    @ApiModelProperty("书签标题")
    private String title;
    /**
     *
     */
    @TableField(value = "create_time")
    private Date createTime;
    /**
     *
     */
    @TableField(value = "update_time")
    private Date updateTime;
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
     * 是否已读 0否 1是
     */
    @TableField(value = "is_read")
    @ApiModelProperty("是否已读 0否 1是")
    private Integer isRead;
    /**
     * 是否失效链接 0否 1是
     */
    @TableField(value = "is_invalid")
    @ApiModelProperty("是否失效链接 0否 1是")
    private Integer isInvalid;
    /**
     * 数据绑定的用户id
     */
    @TableField(value = "user_id")
    @ApiModelProperty("数据绑定的用户id")
    private String userId;
    /**
     * 书签笔记
     */
    @TableField(value = "note")
    @ApiModelProperty("书签笔记")
    private String note;
}