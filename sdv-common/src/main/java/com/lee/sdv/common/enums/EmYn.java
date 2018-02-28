/*
 * Copyright (c) 2016 www.lee.com All rights reserved.
 * 本软件源代码版权归lee所有,未经许可不得任意复制与传播.
 */
package com.lee.sdv.common.enums;

/**
 * 是否有效状态枚举
 */
public enum EmYn {

    //是否有效状态 0无效 1有效

    NO(0, "无效"),
    YES(1, "有效"),
    ;

    private final int value;
    private final String title;

    private EmYn(int value, String title) {
        this.value = value;
        this.title = title;
    }

    public int value() {
        return value;
    }
    public int getValue() {
        return value;
    }


    public String toString() {
        return this.title;
    }

    public static EmYn of(final int value) {
        for (EmYn rpt : EmYn.values()) {
            if (rpt.value == value) {
                return rpt;
            }
        }
        return null;
    }
}
