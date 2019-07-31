package com.example.mall.entity.tness;

import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;



import com.baomidou.mybatisplus.annotations.Version;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author qmt
 * @since 2019-07-22
 */
@Data
@Accessors(chain = true)
@TableName("tness_info")
public class TnessInfo extends Model<TnessInfo> {

    private static final long serialVersionUID = 1L;

    private String id;

    /**
     * 健身类型 1步行；2跑步；3骑行
     */
    @TableField("tness_type")
    private Integer tnessType;

    /**
     * 消耗卡路里
     */
    private Integer calorie;

    /**
     * 运动时长
     */
    private Double duration;

    /**
     * 运动距离
     */
    private Double range;

    /**
     * 数据来源 1远动app;2nick app
     */
    private Integer source;

    @TableField("user_id")
    private String userId;

    private String nickname;

    /**
     * 性别 1 男；2女
     */
    private Integer sex;

    /**
     * 运动时间
     */
    @TableField("duration_time")
    private Date durationTime;

    private String remark;

    @TableField("create_time")
    private Date createTime;


    public static final String ID = "id";

    public static final String TNESS_TYPE = "tness_type";

    public static final String CALORIE = "calorie";

    public static final String DURATION = "duration";

    public static final String RANGE = "range";

    public static final String SOURCE = "source";

    public static final String USER_ID = "user_id";

    public static final String NICKNAME = "nickname";

    public static final String SEX = "sex";

    public static final String DURATION_TIME = "duration_time";

    public static final String REMARK = "remark";

    public static final String CREATE_TIME = "create_time";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
