package com.example.mall.service.wx.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.example.mall.comment.ComemtSatues;
import com.example.mall.comment.WxMenuLevel;
import com.example.mall.entity.wx.WxConfig;
import com.example.mall.entity.wx.WxMenu;
import com.example.mall.mapper.wx.WxMenuMapper;
import com.example.mall.service.wx.WxConfigService;
import com.example.mall.service.wx.WxMenuService;
import com.example.mall.util.JUUIDUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author qmt
 * @since 2019-07-03
 */
@Service
@Slf4j
public class WxMenuServiceImpl extends ServiceImpl<WxMenuMapper, WxMenu> implements WxMenuService {
    @Autowired
    private WxConfigService wxConfigService;

    @Override
    public List<WxMenu> getWxMenus(String shopId) {
        List<WxMenu> resultList = new ArrayList<>();
        //获取所有的一级菜单
        List<WxMenu> parentMenus = this.parentMenus(shopId);
        for (WxMenu parentMenu : parentMenus) {
            resultList.add(parentMenu);
            //获取一级菜单下的二级菜单
            List<WxMenu> childMenus = this.selectList(new EntityWrapper<WxMenu>().eq("parent_id", parentMenu.getId()).and()
                    .eq("menu_level", WxMenuLevel.TOW.getLevel()).and().eq("status", ComemtSatues.NORMAL.getStatus()).and()
                    .eq("shop_id", shopId)
                    .orderBy("menu_order"));
            if (childMenus.size() > 0) {
                resultList.addAll(childMenus);
            }

        }
        return resultList;
    }

    @Override
    public List<WxMenu> parentMenus(String shopId) {
        List<WxMenu> parentMenus = this.selectList(new EntityWrapper<WxMenu>().eq("menu_level", WxMenuLevel.ONE.getLevel()).and()
                .eq("status", ComemtSatues.NORMAL.getStatus()).and().eq("shop_id", shopId)
                .orderBy("menu_order"));
        return parentMenus;
    }

    @Override
    public boolean saveOrUpdateMenu(WxMenu wxMenu) {
        WxMenu selectOne = null;
        if (StringUtils.isNotBlank(wxMenu.getId())) {
            selectOne = this.selectOne(new EntityWrapper<WxMenu>().eq("id", wxMenu.getId()));
        } else {
            selectOne = new WxMenu();
            selectOne.setCreateTime(new Date());
            selectOne.setId(JUUIDUtil.createUuid());
            selectOne.setStatus(ComemtSatues.NORMAL.getStatus());
            selectOne.setShopId(wxMenu.getShopId());
        }
        selectOne.setMenuLevel(wxMenu.getMenuLevel());
        selectOne.setMenuLink(wxMenu.getMenuLink());
        selectOne.setMenuType(wxMenu.getMenuType());
        selectOne.setParentId(wxMenu.getParentId());
        selectOne.setWxMenuName(wxMenu.getWxMenuName());
        selectOne.setRemark(wxMenu.getRemark());
        selectOne.setMenuOrder(this.getOrderNum(wxMenu.getRemark()));
        boolean result = this.insertOrUpdateAllColumn(selectOne);
        return result;

    }

    @Override
    public boolean delMenu(String id) {

        WxMenu wxMenu = this.selectOne(new EntityWrapper<WxMenu>().eq("id", id));
        if (wxMenu.getMenuLevel() == WxMenuLevel.ONE.getLevel()) {
            //如果是一级菜单，也要把它下面的二级菜单给删掉
            this.delete(new EntityWrapper<WxMenu>().eq("parent_id", wxMenu.getId()));
        }
        return this.deleteById(id);
    }

    @Override
    public String createMenuJson(String shopId) {

        WxConfig wxConfig = wxConfigService.selectOne(new EntityWrapper<WxConfig>().eq("shop_id", shopId));
        //获取所有的一级菜单
        List<WxMenu> parentMenus = this.parentMenus(shopId);
        Map<String, Object> resultMap = new HashMap<>();
        List<Map<String, Object>> listParentrenMap = new ArrayList<>();
        Map<String, Object> parentMap = null;
        for (WxMenu parentMenu : parentMenus) {
            parentMap = new HashMap<>();
            //获取一级菜单下的二级菜单
            List<WxMenu> childMenus = this.selectList(new EntityWrapper<WxMenu>().eq("parent_id", parentMenu.getId()).and()
                    .eq("menu_level", WxMenuLevel.TOW.getLevel()).and().eq("status", ComemtSatues.NORMAL.getStatus()).and()
                    .eq("shop_id", shopId)
                    .orderBy("menu_order"));
            List<Map<String, Object>> listChildrenMap = new ArrayList<>();
            if (childMenus.size() > 0) {
                Map<String, Object> map = null;
                for (WxMenu wxMenu : childMenus) {
                    map = new HashMap<>();
                    map.put("type", wxMenu.getMenuType());
                    map.put("name", wxMenu.getWxMenuName());
                    map.put("url", wxConfig.getWebUrl() + wxMenu.getMenuLink());
                    listChildrenMap.add(map);
                }
                parentMap.put("sub_button", listChildrenMap);
                parentMap.put("name", parentMenu.getWxMenuName());
            } else {
                //没有子类的父类
                parentMap.put("type", parentMenu.getMenuType());
                parentMap.put("url", wxConfig.getWebUrl() + parentMenu.getMenuLink());
                parentMap.put("name", parentMenu.getWxMenuName());
            }
            listParentrenMap.add(parentMap);
        }
        resultMap.put("button", listParentrenMap);
        String jsonString = JSON.toJSONString(resultMap);
        return jsonString;


    }

    @Override
    public void createWxMenu(String shopId) {
        String menuJson = this.createMenuJson(shopId);
        String accessToken = wxConfigService.getAccessToken(shopId);
        String url = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=" + accessToken;
        wxConfigService.post_tuLing(url, menuJson);

    }

    public int getOrderNum(String menuOrder) {
        String[] split = menuOrder.split("-");
        return Integer.valueOf(split.length > 1 ? split[1] : split[0]);
    }
}
