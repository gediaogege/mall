package com.example.mall.web.admin;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.example.mall.comment.ComemtSatues;
import com.example.mall.comment.CommentResult;
import com.example.mall.entity.admin.*;
import com.example.mall.pvo.AuthParamVo;
import com.example.mall.pvo.PermissionQueryVo;
import com.example.mall.service.admin.UserAdminService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author qmt
 * @since 2019-06-28
 */
@Controller
@RequestMapping("mall/admin")
@Slf4j
@Api(tags ="UserAdminController",description = "后台用户管理")
public class UserAdminController {
    @Autowired
    private UserAdminService userAdminService;
    @Value("${jwt.tokenHead}")
    private String tokenHead;

    /**
     * 用户登录
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    @ApiOperation("用户登录返回token")
    public CommentResult login(@RequestBody UserAdmin userAdmin) {//post请求
        log.info("用户开始登录。。。,登录用户：{}", userAdmin.getUsername());
        Map<String, Object> map = new HashMap<>(2);
        String token = userAdminService.login(userAdmin);
        map.put("token", token);
        map.put("tokenHead", tokenHead);
        return CommentResult.success(map);

    }

    /**
     * 获取登录用户的信息
     *
     * @return
     */
    @ApiOperation("获取登录用户的信息")
    @ResponseBody
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public CommentResult info() {
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = principal.getUsername();
        return CommentResult.success(username);
    }

    /**
     * 注销
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public CommentResult logout() {
        SecurityContextHolder.getContext().setAuthentication(null);
        return CommentResult.success();
    }

    /**
     * 账户列表
     *
     * @return
     */
    @ApiOperation("用户列表")
    @RequestMapping(value = "/list",method = RequestMethod.POST)
    @ResponseBody
    public CommentResult list() {
        Map<String, Object> map = new HashMap<>();
        List<UserAdmin> userAdmins = userAdminService.selectList(new EntityWrapper<UserAdmin>());
        List<UserAdminExtend> userAdminExtends = userAdminService.userAdminExtend(userAdmins);
        List<Map<String, String>> sysRoles = userAdminService.getSysRoles();
        map.put("users", userAdminExtends);
        map.put("sysRoles", sysRoles);
        return CommentResult.success(map);
    }

    /**
     * 获取用户已拥有的角色
     *
     * @param vo
     * @return
     */
    @ApiOperation("获取用户已拥有的角色")
    @ResponseBody
    @RequestMapping(value = "/getUserRoles",method = RequestMethod.POST)
    public CommentResult getUserRoles(@RequestBody AuthParamVo vo) {
        Map<String, Object> map = new HashMap<>();
        List<String> userRoles = userAdminService.getUserRoles(vo.getUserId());
        map.put("userRoles", userRoles);
        return CommentResult.success(map);
    }


    @ApiOperation("保存或更新")
    @RequestMapping(value = "/saveOrUpdateUser",method = RequestMethod.POST)
    @ResponseBody
    public CommentResult saveOrUpdateUser(@RequestBody AuthParamVo vo) {
        userAdminService.saveOrUpdateUser(vo);
        return CommentResult.success();
    }


    @ApiOperation("角色列表")
    @RequestMapping(value = "/roleList",method = RequestMethod.POST)
    @ResponseBody
    public CommentResult roleList() {
        Map<String, Object> map = new HashMap<>();
        List<RoleInfo> roleInfos = userAdminService.roleList();
        List<RoleInfoExtend> roleInfoExtendList = userAdminService.roleInfoExtend(roleInfos);
        List<Map<String, String>> sysPermission = userAdminService.getSysPermission();
        map.put("roles", roleInfoExtendList);
        map.put("sysPermission", sysPermission);
        return CommentResult.success(map);
    }


    @ApiOperation("获取角色已拥有的权限")
    @ResponseBody
    @RequestMapping(value = "/rolePermissions",method = RequestMethod.POST)
    public CommentResult rolePermissions(@RequestBody AuthParamVo vo) {
        Map<String, Object> map = new HashMap<>();
        List<String> rolePermissions = userAdminService.getPermissionByRoleId(vo.getRoleId());
        map.put("rolePermissions", rolePermissions);
        return CommentResult.success(map);
    }


    @ApiOperation("保存更新角色")
    @RequestMapping(value = "/saveOrUpdateRole",method = RequestMethod.POST)
    @ResponseBody
    public CommentResult saveOrUpdateRole(@RequestBody AuthParamVo vo) {
        userAdminService.saveOrUpdateRole(vo);
        return CommentResult.success();
    }


    @ApiOperation("删除角色")
    @ResponseBody
    @RequestMapping(value = "/delRole",method = RequestMethod.POST)
    public CommentResult delRole(@RequestBody AuthParamVo vo) {
        userAdminService.delRole(vo.getId());
        return CommentResult.success();
    }

    @ApiOperation("权限列表")
    @RequestMapping(value = "/permissionList",method = RequestMethod.POST)
    @ResponseBody
    public CommentResult permissionList(@RequestBody PermissionQueryVo vo) {
        Map<String, Object> map = new HashMap<>();
        Page<PermissionInfo> permissionInfoPage = userAdminService.permissionPage(vo);
        map.put("permissions", permissionInfoPage);
        return CommentResult.success(map);
    }


    @ApiOperation("保存更新权限")
    @RequestMapping(value = "/saveOrUpdatePermission",method = RequestMethod.POST)
    @ResponseBody
    public CommentResult saveOrUpdatePermission(@RequestBody AuthParamVo vo) {
        userAdminService.saveOrUpdatePermission(vo);
        return CommentResult.success();
    }


    @ApiOperation("删除权限")
    @ResponseBody
    @RequestMapping(value = "/delPermission",method = RequestMethod.POST)
    public CommentResult delPermission(@RequestBody AuthParamVo vo) {
        userAdminService.delPermission(vo.getId());
        return CommentResult.success();
    }


}
