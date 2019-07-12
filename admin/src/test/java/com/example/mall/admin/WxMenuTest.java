package com.example.mall.admin;

import com.example.mall.entity.wx.WxConfig;
import com.example.mall.entity.wx.WxMenu;
import com.example.mall.service.wx.WxConfigService;
import com.example.mall.service.wx.WxMenuService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WxMenuTest {
    @Autowired
    private WxMenuService wxMenuService;
    @Autowired
    private WxConfigService wxConfigService;

    @Test
    public void test1() {
        List<WxMenu> wxMenus = wxMenuService.getWxMenus("1");
        System.out.println(wxMenus);
    }

    @Test
    public void test2() {
        String src = "1-3";
        String[] split = src.split("-");
        System.out.println(split.length > 1 ? split[1] : split[0]);
    }

    @Test
    public void test3() {
        wxMenuService.delMenu("9a4bf0aec24e4f5ca7b407cb4013c009");
    }

    @Test
    public void test4() {
        String json = wxMenuService.createMenuJson("1");
        System.out.println(json);
    }

    @Test
    public void test5() {
        String accessToken = wxConfigService.getAccessToken("1");
        System.out.println(accessToken);
    }

}
