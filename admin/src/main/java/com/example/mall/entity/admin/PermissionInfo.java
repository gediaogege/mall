package com.example.mall.entity.admin;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

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
@TableName("permission_info")
public class PermissionInfo extends Model<PermissionInfo> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id")
    private String id;

    @TableField("permission_name")
    private String permissionName;

    private String description;

    /**
     * 权限值
     */
    @TableField("auth_value")
    private String authValue;

    /**
     * 菜单url
     */
    @TableField("menu_url")
    private String menuUrl;

    @TableField("create_time")
    private Date createTime;

    private Integer status;

    /**
     * 上级菜单id
     */
    @TableField("parent_id")
    private String parentId;

    @TableField("menu_level")
    private Integer menuLevel;

    private Integer sort;


    public static final String ID = "id";

    public static final String PERMISSION_NAME = "permission_name";

    public static final String DESCRIPTION = "description";

    public static final String AUTH_VALUE = "auth_value";

    public static final String MENU_URL = "menu_url";

    public static final String CREATE_TIME = "create_time";

    public static final String STATUS = "status";

    public static final String PARENT_ID = "parent_id";

    public static final String MENU_LEVEL = "menu_level";

    public static final String SORT = "sort";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
