package com.example.mall.entity.admin;

import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
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
 * @since 2019-07-01
 */
@Data
@Accessors(chain = true)
@TableName("role_info")
public class RoleInfo extends Model<RoleInfo> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private String id;

    /**
     * 角色名称
     */
    @TableField("role_name")
    private String roleName;

    @TableField("create_time")
    private Date createTime;

    private Integer status;

    /**
     * 角色描述
     */
    private String description;

    /**
     * 排序
     */
    private Integer sort;


    public static final String ID = "id";

    public static final String ROLE_NAME = "role_name";

    public static final String CREATE_TIME = "create_time";

    public static final String STATUS = "status";

    public static final String DESCRIPTION = "description";

    public static final String SORT = "sort";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
