package com.example.mall.pvo;

import lombok.Data;

import java.util.List;

@Data
public class AuthParamVo {
    private String userId;
    private String statue;
    private String username;
    private List <String>roles;
    private String password;
    private String roleId;
    private String id;
    private List<String> permissions;
    private String roleName;
    private String description;
    private String permissionName;
    private String authValue;
    private String menuUrl;
}
