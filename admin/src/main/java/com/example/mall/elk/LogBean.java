package com.example.mall.elk;

import lombok.Data;

/**
 * 日志记录实体
 */
@Data
public class LogBean {
    /**
     * 操作描述
     */
    private String description;

    /**
     * 操作用户
     */
    private String username;

    /**
     * 操作时间
     */
    private Long startTime;

    /**
     * 消耗时间
     */
    private Integer spendTime;

    /**
     * 根路径
     */
    private String basePath;

    /**
     * URI
     */
    private String uri;

    /**
     * URL
     */
    private String url;

    /**
     * 请求类型
     */
    private String methodType;
    /**
     * 请求方法名称
     */
    private String methodName;
    /**
     * IP地址
     */
    private String ip;
    /**
     * 请求参数
     */
    private Object parameter;
    /**
     * 返回结果
     */
    private Object result;
}
