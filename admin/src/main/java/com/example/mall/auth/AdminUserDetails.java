package com.example.mall.auth;

import com.example.mall.entity.admin.PermissionInfo;
import com.example.mall.entity.admin.UserAdmin;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


public class AdminUserDetails implements UserDetails {
    private UserAdmin umsAdmin;
    private List<PermissionInfo> permissionList;

    public AdminUserDetails(UserAdmin umsAdmin, List<PermissionInfo> permissionList) {
        this.umsAdmin = umsAdmin;
        this.permissionList = permissionList;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //返回当前用户的权限
        return permissionList.stream().filter(permissionInfo -> permissionInfo.getAuthValue() != null).map(permissionInfo ->
                new SimpleGrantedAuthority(permissionInfo.getAuthValue())).collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return umsAdmin.getPassword();
    }

    @Override
    public String getUsername() {
        return umsAdmin.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return umsAdmin.getState().equals(1);
    }
}
