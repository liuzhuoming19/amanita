package top.futurenotfound.bookmark.manager.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * 书签
 * @TableName bookmark
 */
@TableName(value ="bookmark")
@Data
public class Bookmark implements Serializable {
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
    private LocalDateTime createTime;

    /**
     * 
     */
    @TableField(value = "update_time")
    private LocalDateTime updateTime;

    /**
     * 书签地址域名
     */
    @TableField(value = "domain")
    private String domain;

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

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}