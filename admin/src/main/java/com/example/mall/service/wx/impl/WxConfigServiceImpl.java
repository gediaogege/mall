package com.example.mall.service.wx.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.example.mall.entity.wx.WxConfig;
import com.example.mall.mapper.wx.WxConfigMapper;
import com.example.mall.service.wx.WxConfigService;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author qmt
 * @since 2019-07-04
 */
@Service
public class WxConfigServiceImpl extends ServiceImpl<WxConfigMapper, WxConfig> implements WxConfigService {
    public static final String GET_ACCESSTOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
    public static String ACCESSTOKEN;//密钥
    public static Long EXPIRESTIME_ACCETOKEN;//密钥有效时间


    /**
     * 获取ACCETOKEN
     *
     * @return
     */
    @Override
    public String getAccessToken(String shopId) {
        /**
         * 票据失效了才会去重新获取
         */
        if (ACCESSTOKEN == null || EXPIRESTIME_ACCETOKEN < System.currentTimeMillis()) {
            WxConfig wxConfig = this.selectOne(new EntityWrapper<WxConfig>().eq("shop_id", shopId));
            String result = post_tuLing(GET_ACCESSTOKEN_URL.replace("APPID", wxConfig.getAppId()).replace("APPSECRET", wxConfig.getAppSecret()), "");
            Map<String, Object> toMap = (Map<String, Object>) JSON.parse(result);
            ACCESSTOKEN = (String) toMap.get("access_token");
            Integer expires_in = (Integer) toMap.get("expires_in");
            long temp = (long) expires_in;
            EXPIRESTIME_ACCETOKEN = (temp - 2000) * 1000 + System.currentTimeMillis();
        }
        return ACCESSTOKEN;

    }


    /**
     * 发送post请求,
     *
     * @throws Exception
     */
    @Override
    public String post_tuLing(String url, String paramStr) {
        InputStream in = null;
        OutputStream os = null;
        String result = "";
        HttpURLConnection conn = null;
        try {
            // 打开和URL之间的连接
            conn = (HttpURLConnection) new URL(url)
                    .openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("Content-Type", "application/json");
            // 发送POST请求须设置
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            os = conn.getOutputStream();
            // 注意编码格式，防止中文乱码
            if (StringUtils.hasText(paramStr)) {
                os.write(paramStr.getBytes("utf-8"));
                os.close();
            }
            in = conn.getInputStream();
            result = StreamUtils.copyToString(in, Charset.forName("UTF-8"));
        } catch (Exception e) {
            InputStream errorStream = conn.getErrorStream();
            try {
                result = StreamUtils.copyToString(errorStream, Charset.forName("UTF-8"));
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        } finally {
            try {
                if (os != null) {
                    os.close();
                }
                if (in != null) {
                    in.close();
                }

            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }
}
