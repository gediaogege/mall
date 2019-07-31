package com.example.mall.service.search;

import com.example.mall.pvo.PermissionQueryVo;
import com.example.mall.search.EsPermissionInfo;
import org.springframework.data.domain.Page;

import java.util.List;

public interface EsPermissionInfoService {
    /**
     * 获取源数据
     *
     * @return
     */
    List<EsPermissionInfo> getSourceData();

    /**
     * 向es中导入数据
     * @return
     */
    int importData();

    /**
     * 搜索
     * @param permissionName
     * @param status
     * @return
     */
    Page<EsPermissionInfo> search(PermissionQueryVo qo);
}
