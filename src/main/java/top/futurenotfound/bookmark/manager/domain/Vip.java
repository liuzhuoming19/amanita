package top.futurenotfound.bookmark.manager.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import top.futurenotfound.bookmark.manager.env.VipType;

import java.io.Serializable;
import java.util.Date;

/**
 * 会员
 * <p>
 * 会员时间分段插入，正常情况下勿进行任何更新操作
 *
 * @author liuzhuoming
 */
@TableName(value = "vip")
@Data
public class Vip implements Serializable {
    private static final long serialVersionUID = 7374482167723383463L;
    @TableId(value = "id")
    private String id;
    /**
     * 用户id
     */
    @TableField(value = "user_id")
    private String userId;
    /**
     * 会员开始时间
     */
    @TableField(value = "start_time")
    private Date startTime;
    /**
     * 会员结束时间
     */
    @TableField(value = "end_time")
    private Date endTime;
    /**
     * 会员类型
     *
     * @see VipType
     */
    @TableField(value = "type")
    private Integer type;

    @TableField(value = "create_time")
    private Date createTime;
    @TableField(value = "update_time")
    private Date updateTime;

}