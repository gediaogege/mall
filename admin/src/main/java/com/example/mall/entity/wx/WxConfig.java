package com.example.mall.entity.wx;

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
 * @since 2019-07-04
 */
@Data
@Accessors(chain = true)
@TableName("wx_config")
public class WxConfig extends Model<WxConfig> {

    private static final long serialVersionUID = 1L;

    private String id;

    @TableField("shop_id")
    private String shopId;

    @TableField("app_id")
    private String appId;

    @TableField("app_secret")
    private String appSecret;

    @TableField("web_url")
    private String webUrl;

    @TableField("server_url")
    private String serverUrl;


    public static final String ID = "id";

    public static final String SHOP_ID = "shop_id";

    public static final String APP_ID = "app_id";

    public static final String APP_SECRET = "app_secret";

    public static final String WEB_URL = "web_url";

    public static final String SERVER_URL = "server_url";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
