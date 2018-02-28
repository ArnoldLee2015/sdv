/*
 * Copyright (c) 2016 www.lee.com All rights reserved.
 * 本软件源代码版权归lee所有,未经许可不得任意复制与传播.
 */
package com.lee.sdv.common.utils.enums;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 枚举工具类
 *
 * @author lipeng
 */
public class EnumUtils {

    private static final Logger LOG = LoggerFactory.getLogger(EnumUtils.class);
    /**
     * 枚举类对应的包路径
     */
    public final static String PACKAGE_NAME = "com.lee.sdv.common.enums";

    /**
     * 枚举类对应的全路径集合
     */
    public static final List<String> ENUM_OBJECT_PATH = PackageUtil.getPackageClasses(PACKAGE_NAME);

    /**
     * 所有枚举对象的 map
     */
    public static final Map<String, Map<Object, Object>> ENUM_MAP = initialEnumMap();

    public static Map<Object, Object> getEnum(String enumName) {
        Map<Object, Object> enumMap = ENUM_MAP.get(enumName);
        return enumMap;
    }

    public static Object getEnumByValue(String enumName, Object enumValue) {
        Map<Object, Object> enumMap = getEnum(enumName);
        Object enumObject = enumMap.get(enumValue);
        return enumObject;
    }

    /**
     * 加载所有枚举对象数据
     */
    private static Map<String, Map<Object, Object>> initialEnumMap() {
        Map<String, Map<Object, Object>> ENUM_MAP = new HashMap<String, Map<Object, Object>>();
        for (String classname : ENUM_OBJECT_PATH) {
            try {
                Map<Object, Object> singleEnumMap = initialSingleEnumMap(classname);
                String enumName = StringUtils.substringAfterLast(classname, ".");
                ENUM_MAP.put(enumName, singleEnumMap);
            } catch (Exception e) {
                LOG.info("初始化枚举类型失败,classname=" + classname, e);
                e.printStackTrace();
            }
        }
        return ENUM_MAP;
    }

    /**
     * 加载每个枚举对象数据
     */
    private static Map<Object, Object> initialSingleEnumMap(String classname) throws Exception {
        Class<?> cls = Class.forName(classname);
        Method method = cls.getMethod("values");
        Map<Object, Object> singleEnumMap = new HashMap<Object, Object>();
        Object[] objectArray = (Object[]) method.invoke(null, null);
        for (Object enumObject : objectArray) {
            Class<?> clz = enumObject.getClass();
            Object objValue = clz.getMethod("getValue").invoke(enumObject);
            singleEnumMap.put(objValue, enumObject);
        }
        return singleEnumMap;
    }

    public static void main(String[] args) {
        System.out.println(ENUM_MAP);
        LOG.info(JSON.toJSONString(ENUM_MAP));
        LOG.info(JSON.toJSONString(ENUM_MAP));

    }

}
