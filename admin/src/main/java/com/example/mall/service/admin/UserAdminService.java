package com.example.mall.service.admin;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.example.mall.entity.admin.*;
import com.example.mall.pvo.AuthParamVo;
import com.example.mall.pvo.PermissionQueryVo;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author qmt
 * @since 2019-06-28
 */
public interface UserAdminService extends IService<UserAdmin> {
    /**
     * 通过用户名获取用户的权限集合
     *
     * @param username
     * @return
     */
    List<PermissionInfo> getUserPermissionByUserName(String username);

    /**
     * 用户登录
     *
     * @param userAdmin 登录用户信息
     * @return 登录成功返回token
     */
    String login(UserAdmin userAdmin);

    /**
     * 获取系统已有的角色
     *
     * @return
     */
    List<Map<String, String>> getSysRoles();

    /**
     * 获取用户拥有的角色
     *
     * @return
     */
    List<String> getUserRoles(String userId);

    /**
     * 保存或更新用户
     *
     * @param vo
     */
    void saveOrUpdateUser(AuthParamVo vo);

    List<UserAdminExtend> userAdminExtend(List<UserAdmin> userAdmins);

    /**
     * 获取角色列表
     *
     * @return
     */
    List<RoleInfo> roleList();

    List<RoleInfoExtend> roleInfoExtend(List<RoleInfo> roleInfos);

    /**
     * 获取角色拥有的权限
     *
     * @param roleId
     * @return
     */
    List<String> getPermissionByRoleId(String roleId);

    /**
     * 获取系统已有的权限
     *
     * @return
     */
    List<Map<String, String>> getSysPermission();

    /**
     * 保存或更新角色
     *
     * @param vo
     */
    void saveOrUpdateRole(AuthParamVo vo);

    /**
     * 删除角色
     *
     * @param roleId
     */
    void delRole(String roleId);

    /**
     * 权限列表带分页
     *
     * @param permissionQueryVo
     * @return
     */
    Page<PermissionInfo> permissionPage(PermissionQueryVo permissionQueryVo);

    /**
     * 保存或更新权限
     * @param vo
     */
    void saveOrUpdatePermission(AuthParamVo vo);

    /**
     * 删除权限
     * @param permissionId
     */
    void delPermission(String permissionId);
}
