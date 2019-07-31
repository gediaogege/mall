package com.example.mall.search;

import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;

/**
 * 权限搜索实体
 */
@Data
@Document(indexName = "auth",type = "permission",shards = 1,replicas = 0)
public class EsPermissionInfo {
    private static final long serialVersionUID = 1L;

    private String id;
    @Field(analyzer = "ik_max_word",type = FieldType.Text)
    private String permissionName;
    @Field(analyzer = "ik_max_word",type = FieldType.Text)
    private String description;

    private String menuUrl;

    private Date createTime;

    private String authValue;
    @Field(type = FieldType.Integer)
    private Integer status;

    private String parentId;
    @Field(type = FieldType.Keyword)
    private Integer menuLevel;

    private Integer sort;
}
