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
 * @since 2019-06-28
 */
@Data
@Accessors(chain = true)
@TableName("user_admin")
public class UserAdmin extends Model<UserAdmin> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id")
    private String id;

    private String username;

    private String password;

    @TableField("create_time")
    private Date createTime;

    private Integer state;

    private String remark;


    public static final String ID = "id";

    public static final String USERNAME = "username";

    public static final String PASSWORD = "password";

    public static final String CREATE_TIME = "create_time";

    public static final String STATE = "state";

    public static final String REMARK = "remark";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
