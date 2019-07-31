package com.example.mall.entity.cos;

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
 * @since 2019-07-19
 */
@Data
@Accessors(chain = true)
@TableName("cos_info")
public class CosInfo extends Model<CosInfo> {

    private static final long serialVersionUID = 1L;

    private String id;

    @TableField("backet_name")
    private String backetName;

    private String key;

    private String url;

    @TableField("relate_id")
    private String relateId;

    private Integer status;

    @TableField("relate_type")
    private Integer relateType;


    public static final String ID = "id";

    public static final String BACKET_NAME = "backet_name";

    public static final String KEY = "key";

    public static final String URL = "url";

    public static final String RELATE_ID = "relate_id";

    public static final String STATUS = "status";

    public static final String RELATE_TYPE = "relate_type";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
