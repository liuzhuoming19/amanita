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
    private static final long serialVersionUID = -5758215339894064800L;
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
     * 是否开启书签删除历史（开启后可在回收站保存30天）（功能类设置） 0否1是
     */
    @TableField(value = "allow_feat_bookmark_deleted_history")
    private Integer allowFeatBookmarkDeletedHistory;

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