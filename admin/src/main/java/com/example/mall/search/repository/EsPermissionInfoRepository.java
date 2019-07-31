package com.example.mall.search.repository;

import com.example.mall.search.EsPermissionInfo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * 权限Es操作类
 */
public interface EsPermissionInfoRepository extends ElasticsearchRepository<EsPermissionInfo, String> {
}
