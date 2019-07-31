package com.example.mall.web.wx;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.example.mall.comment.ComemtSatues;
import com.example.mall.comment.CommentResult;
import com.example.mall.comment.WxMenuLevel;
import com.example.mall.entity.wx.WxMenu;
import com.example.mall.pvo.WxMenuParamVo;
import com.example.mall.service.wx.WxMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author qmt
 * @since 2019-07-03
 */
@Controller
@RequestMapping("mall/wx")
@Api(tags = "WxMenuController", description = "微信菜单管理")
public class WxMenuController {
    @Autowired
    private WxMenuService wxMenuService;


    @RequestMapping(value = "/menuList", method = RequestMethod.POST)
    @ResponseBody
    @PreAuthorize("hasAuthority('wx:menuList')")
    @ApiOperation("获取菜单列表")
    public CommentResult menuList(@RequestBody  WxMenuParamVo vo) {
        Map<String, Object> map = new HashMap<>();
        List<WxMenu> wxMenus = wxMenuService.getWxMenus(vo.getShopId());
        map.put("wxMenus", wxMenus);
        //返回所有的一级菜单
        List<WxMenu> parents = wxMenuService.selectList(new EntityWrapper<WxMenu>().eq("menu_level", WxMenuLevel.ONE.getLevel()).and()
                .eq("status", ComemtSatues.NORMAL.getStatus()).and().eq("shop_id", vo.getShopId()));
        map.put("parents", parents);
        return CommentResult.success(map);
    }


    @ResponseBody
    @RequestMapping(value = "/saveOrUpdateMenu", method = RequestMethod.POST)
    @ApiOperation("保存或更新")
    public CommentResult saveOrUpdateMenu(@RequestBody WxMenu wxMenu) {
        boolean updateMenu = wxMenuService.saveOrUpdateMenu(wxMenu);
        return CommentResult.success();
    }


    @ApiOperation("删除菜单")
    @RequestMapping(value = "/delMenu", method = RequestMethod.POST)
    @ResponseBody
    public CommentResult delMenu(@RequestBody WxMenuParamVo vo) {
        boolean delMenu = wxMenuService.delMenu(vo.getId());
        return CommentResult.success();
    }


    @RequestMapping(value = "/createWxMenu", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("发布微信菜单")
    public CommentResult createWxMenu(@RequestBody WxMenuParamVo vo) {
        wxMenuService.createWxMenu(vo.getShopId());
        return CommentResult.success();

    }

}
