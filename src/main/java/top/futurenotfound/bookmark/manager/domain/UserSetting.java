package top.futurenotfound.bookmark.manager.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户设置
 *
 * @author liuzhuoming
 */
@TableName(value = "user_setting")
@Data
public class UserSetting implements Serializable {
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
    /**
     *
     */
    @TableId(value = "id")
    private String id;
    /**
     * 用户id
     */
    @TableField(value = "user_id")
    private String userId;
    /**
     * 是否开启页面简述存档
     */
    @TableField(value = "allow_excerpt_page_archive")
    private Integer allowExcerptPageArchive;
    /**
     * 是否开启页面全文存档
     */
    @TableField(value = "allow_full_page_archive")
    private Integer allowFullPageArchive;
    /**
     * 是否开启书签修改历史（开启后可回溯历史）
     */
    @TableField(value = "allow_bookmark_change_history")
    private Integer allowBookmarkChangeHistory;
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
}