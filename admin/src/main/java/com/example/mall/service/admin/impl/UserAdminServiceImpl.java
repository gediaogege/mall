package com.example.mall.service.admin.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.example.mall.comment.ComemtSatues;
import com.example.mall.entity.admin.*;
import com.example.mall.mapper.admin.UserAdminMapper;
import com.example.mall.service.admin.*;
import com.example.mall.util.CollectionUtils;
import com.example.mall.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author qmt
 * @since 2019-06-28
 */
@Service
public class UserAdminServiceImpl extends ServiceImpl<UserAdminMapper, UserAdmin> implements UserAdminService {
    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private RolePermissionService rolePermissionService;
    @Autowired
    private PermissionInfoService permissionInfoService;
    @Autowired
    private RoleInfoService roleInfoService;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    public List<PermissionInfo> getUserPermissionByUserName(String username) {
        List<PermissionInfo> permissionInfoList = new ArrayList<>();
        UserAdmin userAdmin = this.selectOne(new EntityWrapper<UserAdmin>().eq("username", username));
        if (userAdmin == null || ComemtSatues.DISABLE.getStatus() == userAdmin.getState()) {
            return permissionInfoList;
        }
        List<UserRole> userRoles = userRoleService.selectList(new EntityWrapper<UserRole>().eq("user_id", userAdmin.getId()));
        List<Object> roleIds = CollectionUtils.extractToList(userRoles, "roleId");
        if (roleIds.size() == 0) {
            return permissionInfoList;
        }
        List<RoleInfo> roleInfos = roleInfoService.selectList(new EntityWrapper<RoleInfo>().in("id", roleIds).and().eq("status", ComemtSatues.NORMAL.getStatus()));
        List<Object> roles = CollectionUtils.extractToList(roleInfos, "id");
        if (roles.size() == 0) {
            return permissionInfoList;
        }
        List<RolePermission> rolePermissions = rolePermissionService.selectList(new EntityWrapper<RolePermission>().in("role_id", roles));
        List<Object> permissionIds = CollectionUtils.extractToList(rolePermissions, "permissionId");
        if (permissionIds.size() == 0) {
            return permissionInfoList;
        }
        permissionInfoList = permissionInfoService.selectList(new EntityWrapper<PermissionInfo>().in("id", permissionIds).and().eq("status", ComemtSatues.NORMAL.getStatus()));
        return permissionInfoList;
    }

    @Override
    public String login(UserAdmin userAdmin) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(userAdmin.getUsername());
        if (userDetails == null || !userAdmin.getPassword().equals(userDetails.getPassword())) {
            throw new RuntimeException("账号密码不正确");
        }
        //把当前用户进行认证
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        //生成对应的token
        String token = jwtTokenUtil.generateToken(userDetails);
        //登录日志
        return token;
    }
}
