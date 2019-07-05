package com.example.mall.service.wx;

import com.baomidou.mybatisplus.service.IService;
import com.example.mall.entity.wx.WxMenu;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author qmt
 * @since 2019-07-03
 */
public interface WxMenuService extends IService<WxMenu> {
    /**
     * 获取微信菜单列表
     *
     * @return
     */
    List<WxMenu> getWxMenus(String shopId);

    /**
     * 获取所有的一级菜单
     * @param shopId
     * @return
     */
    List<WxMenu> parentMenus(String shopId);

    /**
     * 保存或更新微信菜单
     *
     * @param wxMenu
     * @return
     */
    boolean saveOrUpdateMenu(WxMenu wxMenu);

    /**
     * 删除菜单
     * @param id
     * @return
     */
    boolean delMenu(String id);

    /**
     * 创建菜单json串
     * @param shopId
     * @return
     */
    String createMenuJson(String shopId);

    /**
     * 创建微信菜单
     * @param shopId
     * @return
     */
    void createWxMenu(String shopId);
}
