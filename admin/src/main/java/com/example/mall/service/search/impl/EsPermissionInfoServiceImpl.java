package com.example.mall.service.search.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.example.mall.entity.admin.PermissionInfo;
import com.example.mall.pvo.PermissionQueryVo;
import com.example.mall.search.EsPermissionInfo;
import com.example.mall.search.repository.EsPermissionInfoRepository;
import com.example.mall.service.admin.PermissionInfoService;
import com.example.mall.service.search.EsPermissionInfoService;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class EsPermissionInfoServiceImpl implements EsPermissionInfoService {
    @Autowired
    private PermissionInfoService permissionInfoService;
    @Autowired
    private EsPermissionInfoRepository repository;

    @Override
    public List<EsPermissionInfo> getSourceData() {
        List<PermissionInfo> permissionInfos = permissionInfoService.selectList(new EntityWrapper<>());
        return this.handleData(permissionInfos);
    }

    @Override
    public int importData() {
        repository.deleteAll();
        List<EsPermissionInfo> sourceData = this.getSourceData();
        repository.saveAll(sourceData);
        return sourceData.size();
    }

    @Override
    public Page<EsPermissionInfo> search(PermissionQueryVo qo) {
        qo.setCurrentPage(0);
        PageRequest pageRequest = PageRequest.of(qo.getCurrentPage(), qo.getPageSize());
        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
        nativeSearchQueryBuilder.withPageable(pageRequest);
        if (StringUtils.isNotBlank(qo.getPermissionName())){
            nativeSearchQueryBuilder.withQuery(QueryBuilders.matchQuery("permissionName",qo.getPermissionName()));
        }
        //nativeSearchQueryBuilder.withQuery(QueryBuilders.matchQuery("status",qo.getStatus()));
        Page<EsPermissionInfo> esPermissionInfos = repository.search(nativeSearchQueryBuilder.build());
        return esPermissionInfos;
    }


    private List<EsPermissionInfo> handleData(List<PermissionInfo> permissionInfos) {
        List<EsPermissionInfo> list = new ArrayList<>();
        EsPermissionInfo esp = null;
        for (PermissionInfo permissionInfo : permissionInfos) {
            esp = new EsPermissionInfo();
            esp.setAuthValue(permissionInfo.getAuthValue());
            esp.setCreateTime(permissionInfo.getCreateTime());
            esp.setDescription(permissionInfo.getDescription());
            esp.setId(permissionInfo.getId());
            esp.setMenuLevel(permissionInfo.getMenuLevel());
            esp.setMenuUrl(permissionInfo.getMenuUrl());
            esp.setParentId(permissionInfo.getParentId());
            esp.setPermissionName(permissionInfo.getPermissionName());
            esp.setSort(permissionInfo.getSort());
            esp.setStatus(permissionInfo.getStatus());
            list.add(esp);
        }
        return list;
    }
}
