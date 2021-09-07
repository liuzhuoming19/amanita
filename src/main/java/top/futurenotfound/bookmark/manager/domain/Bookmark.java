package top.futurenotfound.bookmark.manager.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 书签
 *
 * @TableName bookmark
 */
@TableName(value = "bookmark")
@Data
public class Bookmark {
    /**
     *
     */
    @TableId(value = "id")
    private String id;
    /**
     * 书签地址
     */
    @TableField(value = "url")
    private String url;
    /**
     * 书签标题
     */
    @TableField(value = "title")
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
    private String host;
    /**
     * 页面摘要
     */
    @TableField(value = "excerpt")
    private String excerpt;
    /**
     * 是否已读 0否 1是
     */
    @TableField(value = "is_read")
    private Integer isRead;
    /**
     * 是否失效链接 0否 1是
     */
    @TableField(value = "is_invalid")
    private Integer isInvalid;
    /**
     * 数据绑定的用户id
     */
    @TableField(value = "user_id")
    private String userId;
    /**
     * 书签笔记
     */
    @TableField(value = "note")
    private String note;
}