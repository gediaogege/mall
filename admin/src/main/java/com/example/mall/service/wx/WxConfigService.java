package com.example.mall.service.wx;

import com.baomidou.mybatisplus.service.IService;
import com.example.mall.entity.wx.WxConfig;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author qmt
 * @since 2019-07-04
 */
public interface WxConfigService extends IService<WxConfig> {
    String getAccessToken(String shopId);
    String post_tuLing(String url, String paramStr);

}
