package com.example.mall.entity.admin;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RoleInfoExtend extends RoleInfo {
    private String permissions;
}
