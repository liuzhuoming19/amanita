package top.futurenotfound.bookmark.manager.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 用户设置
 *
 * @author liuzhuoming
 */
@TableName(value = "user_setting")
@Data
public class UserSetting {
    @TableId(value = "id")
    private String id;
    /**
     * 用户id
     */
    @TableField(value = "user_id")
    private String userId;

    /**
     * 是否开启页面简述存档（功能类设置）
     */
    @TableField(value = "allow_feat_excerpt_page_archive")
    private Integer allowFeatExcerptPageArchive;
    /**
     * 是否开启页面全文存档（功能类设置）
     */
    @TableField(value = "allow_feat_full_page_archive")
    private Integer allowFeatFullPageArchive;
    /**
     * 是否开启书签修改历史（开启后可回溯历史）（功能类设置）
     */
    @TableField(value = "allow_feat_bookmark_change_history")
    private Integer allowFeatBookmarkChangeHistory;

    /**
     * 是否开启精简版页面（UI类设置）
     */
    @TableField(value = "allow_ui_page_lite")
    private Integer allowUiPageLite;

    @TableField(value = "create_time")
    private Date createTime;
    @TableField(value = "update_time")
    private Date updateTime;
}