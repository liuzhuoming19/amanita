package top.futurenotfound.bookmark.manager.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 标签
 *
 * @author liuzhuoming
 */
@TableName(value = "tag")
@Data
public class Tag {
    @TableId(value = "id")
    private String id;
    /**
     * 标签名称
     */
    @TableField(value = "name")
    private String name;
    /**
     * 标签颜色
     */
    @TableField(value = "color")
    private String color;

    @TableField(value = "create_time")
    private Date createTime;
    @TableField(value = "update_time")
    private Date updateTime;
}