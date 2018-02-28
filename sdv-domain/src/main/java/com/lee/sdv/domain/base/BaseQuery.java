/*
 * Copyright (c) 2016 www.lee.com All rights reserved.
 * 本软件源代码版权归lee所有,未经许可不得任意复制与传播.
 */
package com.lee.sdv.domain.base;

import com.alibaba.fastjson.annotation.JSONField;
import org.apache.commons.lang.StringUtils;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * 基础查询类
 *
 * @author lipeng
 */
class BaseQuery implements Serializable {
    private static final long serialVersionUID = 1L;

    private transient Integer startIndex;// 开始索引

    private transient Integer endIndex;// 结束索引

    private transient String orderField;// 排序字段

    private transient String orderFieldType;// 排序字段类型

    private transient Map<String, Object> queryData;// 查询扩展

    private transient String keyword;// 关键则查询

    @JSONField(serialize = false)
    public Integer getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(Integer startIndex) {
        this.startIndex = startIndex;
    }

    @JSONField(serialize = false)
    public Integer getEndIndex() {
        return endIndex;
    }

    public void setEndIndex(Integer endIndex) {
        this.endIndex = endIndex;
    }

    //每页显示条数
    @JSONField(serialize = false)
    public Integer getPageSize() {
        if (endIndex != null && startIndex != null) {
            return endIndex - startIndex;
        }
        return null;
    }

    @JSONField(serialize = false)
    public String getOrderField() {
        return getValidateOrderField(orderField);
    }

    public void setOrderField(String orderField) {
        this.orderField = getValidateOrderField(orderField);
    }

    /**
     * 获取有效的排序字段,防止sql注入
     *
     * @param orderField
     * @return
     */
    private String getValidateOrderField(String orderField) {
        if (StringUtils.isBlank(orderField)) {
            return orderField;
        }
        // 只能包含 字段 asc|desc,字段 格式,否则返回id
        String regEx = "^((([A-Z,a-z,0-9,_]+\\s+(asc|desc)\\s*,\\s*)*[A-Z,a-z,0-9,_]+\\s*)|(convert([A-Z,a-z,0-9,_]+ using gbk)))$";
        boolean result = Pattern.compile(regEx).matcher(orderField.trim().toLowerCase()).find();
        if (!result) {
            return "id";
        }
        return orderField;
    }

    @JSONField(serialize = false)
    public String getOrderFieldType() {
        if ("DESC".equalsIgnoreCase(orderFieldType) || "ASC".equalsIgnoreCase(orderFieldType)) {
            return orderFieldType.toUpperCase();
        }
        return "DESC";
    }

    @JSONField(serialize = false)
    public String getOrderFieldNextType() {
        if ("ASC".equalsIgnoreCase(orderFieldType)) {
            return "DESC";
        }
        return "ASC";
    }

    public void setOrderFieldType(String orderFieldType) {
        this.orderFieldType = orderFieldType;
    }

    @JSONField(serialize = false)
    public Map<String, Object> getQueryData() {
        if (queryData != null && queryData.size() > 0) {
            return queryData;
        }
        return null;
    }

    //添加其它查询数据
    public void addQueryData(String key, Object value) {
        if (queryData == null) {
            queryData = new HashMap<String, Object>();
        }
        queryData.put(key, value);
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getkeyword() {
        return keyword;
    }
}
