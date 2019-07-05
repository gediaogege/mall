package com.example.mall.entity.wx;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
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
 * @since 2019-07-04
 */
@Data
@Accessors(chain = true)
@TableName("wx_menu")
public class WxMenu extends Model<WxMenu> {

    private static final long serialVersionUID = 1L;

    private String id;

    @TableField("parent_id")
    private String parentId;

    /**
     * 菜单名称
     */
    @TableField("wx_menu_name")
    private String wxMenuName;

    /**
     * 菜单等级
     */
    @TableField("menu_level")
    private Integer menuLevel;

    /**
     * 菜单类型
     */
    @TableField("menu_type")
    private String menuType;

    /**
     * 菜单链接
     */
    @TableField("menu_link")
    private String menuLink;

    @TableField("create_time")
    private Date createTime;

    /**
     * 是否启用
     */
    private Integer status;

    private String remark;

    /**
     * 排序
     */
    @TableField("menu_order")
    private Integer menuOrder;

    @TableField("shop_id")
    private String shopId;


    public static final String ID = "id";

    public static final String PARENT_ID = "parent_id";

    public static final String WX_MENU_NAME = "wx_menu_name";

    public static final String MENU_LEVEL = "menu_level";

    public static final String MENU_TYPE = "menu_type";

    public static final String MENU_LINK = "menu_link";

    public static final String CREATE_TIME = "create_time";

    public static final String STATUS = "status";

    public static final String REMARK = "remark";

    public static final String MENU_ORDER = "menu_order";

    public static final String SHOP_ID = "shop_id";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
