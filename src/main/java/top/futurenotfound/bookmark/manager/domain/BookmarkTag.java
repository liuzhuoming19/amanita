package top.futurenotfound.bookmark.manager.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 书签标签
 *
 * @author liuzhuoming
 */
@TableName(value = "bookmark_tag")
@Data
public class BookmarkTag {
    @TableId(value = "id")
    private String id;
    /**
     * 书签id
     */
    @TableField(value = "bookmark_id")
    private String bookmarkId;
    /**
     * 标签id
     */
    @TableField(value = "tag_id")
    private String tagId;

    @TableField(value = "create_time")
    private Date createTime;
    @TableField(value = "update_time")
    private Date updateTime;
}