package com.example.mall.service.admin.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.example.mall.comment.ComemtSatues;
import com.example.mall.entity.admin.*;
import com.example.mall.mapper.admin.UserAdminMapper;
import com.example.mall.pvo.AuthParamVo;
import com.example.mall.pvo.PermissionQueryVo;
import com.example.mall.service.admin.*;
import com.example.mall.util.CollectionUtils;
import com.example.mall.util.JUUIDUtil;
import com.example.mall.util.JwtTokenUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author qmt
 * @since 2019-06-28
 */
@Transactional
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
    @Autowired
    private PasswordEncoder passwordEncoder;

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
        if (userDetails == null || !passwordEncoder.matches(userAdmin.getPassword(), userDetails.getPassword())) {
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

    @Override
    public List<Map<String, String>> getSysRoles() {
        Map<String, String> map = null;
        List<Map<String, String>> list = new ArrayList<>();
        List<RoleInfo> roleInfos = roleInfoService.selectList(new EntityWrapper<RoleInfo>());
        for (RoleInfo roleInfo : roleInfos) {
            map = new HashMap<>();
            map.put("key", roleInfo.getId());
            map.put("label", roleInfo.getRoleName());
            if (roleInfo.getStatus() == ComemtSatues.DISABLE.getStatus()) {
                map.put("disabled", roleInfo.getId());
            }
            list.add(map);
        }
        return list;
    }

    @Override
    public List<String> getUserRoles(String userId) {
        List<String> resultList = new ArrayList<>();
        List<UserRole> userRoles = userRoleService.selectList(new EntityWrapper<UserRole>().eq("user_id", userId));
        List<Object> roleIds = CollectionUtils.extractToList(userRoles, "roleId");
        if (roleIds.size() == 0) {
            return resultList;
        }
        List<RoleInfo> roleInfos = roleInfoService.selectList(new EntityWrapper<RoleInfo>().in("id", roleIds).and().eq("status", ComemtSatues.NORMAL.getStatus()));
        return CollectionUtils.extractToList(roleInfos, "id");
    }

    @Override
    public void saveOrUpdateUser(AuthParamVo vo) {
        UserAdmin userAdmin = null;
        if (StringUtils.isBlank(vo.getUserId())) {
            userAdmin = new UserAdmin();
            userAdmin.setId(JUUIDUtil.createUuid());
            userAdmin.setCreateTime(new Date());
            //将密码进行加密操作
            String encodePassword = passwordEncoder.encode(StringUtils.isNotBlank(vo.getPassword()) ? vo.getPassword() : "123456");
            userAdmin.setPassword(encodePassword);
        } else {
            userAdmin = this.selectById(vo.getUserId());
        }
        //处理和角色的关系
        //先打破原有的关系
        userRoleService.delete(new EntityWrapper<UserRole>().eq("user_id", vo.getUserId()));
        UserRole userRole = null;
        for (String roleId : vo.getRoles()) {
            userRole = new UserRole();
            userRole.setUserId(userAdmin.getId());
            userRole.setRoleId(roleId);
            userRole.setId(JUUIDUtil.createUuid());
            userRoleService.insert(userRole);
        }
        userAdmin.setState(Integer.valueOf(vo.getStatue()));
        userAdmin.setUsername(vo.getUsername());
        this.insertOrUpdateAllColumn(userAdmin);
    }

    @Override
    public List<UserAdminExtend> userAdminExtend(List<UserAdmin> userAdmins) {
        List<UserAdminExtend> resultList = new ArrayList<>(userAdmins.size());
        UserAdminExtend userAdminExtend = null;
        String roleName = "";
        for (UserAdmin userAdmin : userAdmins) {
            userAdminExtend = new UserAdminExtend();
            userAdminExtend.setId(userAdmin.getId());
            userAdminExtend.setCreateTime(userAdmin.getCreateTime());
            userAdminExtend.setPassword(userAdmin.getPassword());
            userAdminExtend.setState(userAdmin.getState());
            userAdminExtend.setUsername(userAdmin.getUsername());
            userAdminExtend.setRemark(userAdmin.getRemark());
            List<String> userRoles = this.getUserRoles(userAdmin.getId());
            if (userRoles.size() > 0) {
                List<RoleInfo> roleInfos = roleInfoService.selectList(new EntityWrapper<RoleInfo>().in("id", userRoles));
                List<Object> roleNames = CollectionUtils.extractToList(roleInfos, "roleName");
                roleName = StringUtils.join(roleNames, ",");
                userAdminExtend.setRoles(roleName);
            }
            resultList.add(userAdminExtend);
        }
        return resultList;
    }

    @Override
    public List<RoleInfo> roleList() {
        return roleInfoService.selectList(new EntityWrapper<RoleInfo>());
    }

    @Override
    public List<RoleInfoExtend> roleInfoExtend(List<RoleInfo> roleInfos) {
        List<RoleInfoExtend> roleInfoExtendList = new ArrayList<>(roleInfos.size());
        RoleInfoExtend roleInfoExtend = null;
        String permissionName = "";
        for (RoleInfo roleInfo : roleInfos) {
            roleInfoExtend = new RoleInfoExtend();
            roleInfoExtend.setCreateTime(roleInfo.getCreateTime());
            roleInfoExtend.setDescription(roleInfo.getDescription());
            roleInfoExtend.setId(roleInfo.getId());
            roleInfoExtend.setRoleName(roleInfo.getRoleName());
            roleInfoExtend.setSort(roleInfo.getSort());
            roleInfoExtend.setStatus(roleInfo.getStatus());
            List<String> permissions = this.getPermissionByRoleId(roleInfo.getId());
            if (permissions.size() > 0) {
                List<PermissionInfo> permissionInfoList = permissionInfoService.selectList(new EntityWrapper<PermissionInfo>().in("id", permissions));
                List<Object> permissionNames = CollectionUtils.extractToList(permissionInfoList, "permissionName");
                permissionName = StringUtils.join(permissionNames, ",");
                roleInfoExtend.setPermissions(permissionName);
            }
            roleInfoExtendList.add(roleInfoExtend);
        }
        return roleInfoExtendList;
    }

    @Override
    public List<String> getPermissionByRoleId(String roleId) {
        List<String> resultList = new ArrayList<>();
        List<RolePermission> rolePermissions = rolePermissionService.selectList(new EntityWrapper<RolePermission>().eq("role_id", roleId));
        List<Object> permissionIds = CollectionUtils.extractToList(rolePermissions, "permissionId");
        if (permissionIds.size() > 0) {
            List<PermissionInfo> permissionInfos = permissionInfoService.selectList(new EntityWrapper<PermissionInfo>().in("id", permissionIds).and().eq("status", ComemtSatues.NORMAL.getStatus()));
            resultList = CollectionUtils.extractToList(permissionInfos, "id");
        }
        return resultList;
    }

    @Override
    public List<Map<String, String>> getSysPermission() {
        Map<String, String> map = null;
        List<Map<String, String>> list = new ArrayList<>();
        List<PermissionInfo> permissionInfoList = permissionInfoService.selectList(new EntityWrapper<PermissionInfo>());
        for (PermissionInfo permissionInfo : permissionInfoList) {
            map = new HashMap<>();
            map.put("key", permissionInfo.getId());
            map.put("label", permissionInfo.getPermissionName());
            if (permissionInfo.getStatus() == ComemtSatues.DISABLE.getStatus()) {
                map.put("disabled", permissionInfo.getId());
            }
            list.add(map);
        }
        return list;
    }

    @Override
    public void saveOrUpdateRole(AuthParamVo vo) {
        RoleInfo roleInfo = null;
        if (StringUtils.isNotBlank(vo.getId())) {
            roleInfo = roleInfoService.selectById(vo.getId());
        } else {
            roleInfo = new RoleInfo();
            roleInfo.setId(JUUIDUtil.createUuid());
            roleInfo.setCreateTime(new Date());
        }
        roleInfo.setDescription(vo.getDescription());
        roleInfo.setRoleName(vo.getRoleName());
        roleInfo.setStatus(Integer.valueOf(vo.getStatue()));
        //维护和权限的关系
        rolePermissionService.delete(new EntityWrapper<RolePermission>().eq("role_id", vo.getId()));
        RolePermission rolePermission = null;
        for (String permissionId : vo.getPermissions()) {
            rolePermission = new RolePermission();
            rolePermission.setId(JUUIDUtil.createUuid());
            rolePermission.setRoleId(roleInfo.getId());
            rolePermission.setPermissionId(permissionId);
            rolePermissionService.insert(rolePermission);
        }
        roleInfoService.insertOrUpdateAllColumn(roleInfo);
    }

    @Override
    public void delRole(String roleId) {
        //解除用户和角色的关系
        userRoleService.delete(new EntityWrapper<UserRole>().eq("role_id", roleId));
        //解除角色和权限的关系
        rolePermissionService.delete(new EntityWrapper<RolePermission>().eq("role_id", roleId));
        //删除角色
        roleInfoService.deleteById(roleId);
    }

    @Override
    public Page<PermissionInfo> permissionPage(PermissionQueryVo permissionQueryVo) {
        EntityWrapper<PermissionInfo> ew = new EntityWrapper<>();
        if (StringUtils.isNotBlank(permissionQueryVo.getPermissionName())) {
            ew.eq("permission_name", permissionQueryVo.getPermissionName());
        }
        ew.and().eq("status", permissionQueryVo.getStatus());
        ew.orderBy("create_time desc");
        Page<PermissionInfo> page = new Page<>(permissionQueryVo.getCurrentPage(), permissionQueryVo.getPageSize());
        Page<PermissionInfo> permissionInfoPage = permissionInfoService.selectPage(page, ew);
        return permissionInfoPage;
    }

    @Override
    public void saveOrUpdatePermission(AuthParamVo vo) {
        PermissionInfo permissionInfo = null;
        if (StringUtils.isNotBlank(vo.getId())) {
            permissionInfo = permissionInfoService.selectById(vo.getId());
        } else {
            permissionInfo = new PermissionInfo();
            permissionInfo.setId(JUUIDUtil.createUuid());
            permissionInfo.setCreateTime(new Date());
        }
        permissionInfo.setAuthValue(vo.getAuthValue());
        permissionInfo.setMenuUrl(vo.getMenuUrl());
        permissionInfo.setDescription(vo.getDescription());
        permissionInfo.setStatus(Integer.valueOf(vo.getStatue()));
        permissionInfo.setPermissionName(vo.getPermissionName());
        permissionInfoService.insertOrUpdateAllColumn(permissionInfo);
    }

    @Override
    public void delPermission(String permissionId) {
        rolePermissionService.delete(new EntityWrapper<RolePermission>().eq("permission_id", permissionId));
        permissionInfoService.deleteById(permissionId);
    }
}
