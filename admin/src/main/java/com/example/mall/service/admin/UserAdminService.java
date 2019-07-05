package com.example.mall.service.admin;

import com.baomidou.mybatisplus.service.IService;
import com.example.mall.entity.admin.PermissionInfo;
import com.example.mall.entity.admin.UserAdmin;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author qmt
 * @since 2019-06-28
 */
public interface UserAdminService extends IService<UserAdmin> {
    /**
     * 通过用户名获取用户的权限集合
     * @param username
     * @return
     */
    List<PermissionInfo> getUserPermissionByUserName(String username);

    /**
     * 用户登录
     * @param userAdmin 登录用户信息
     * @return 登录成功返回token
     */
    String login(UserAdmin userAdmin);
}
