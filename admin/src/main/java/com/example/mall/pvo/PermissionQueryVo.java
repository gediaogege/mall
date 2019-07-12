package com.example.mall.pvo;

import lombok.Data;


@Data
public class PermissionQueryVo {
    private int pageSize = 5;
    private int currentPage = 1;
    private String permissionName;
    private int status;
}
