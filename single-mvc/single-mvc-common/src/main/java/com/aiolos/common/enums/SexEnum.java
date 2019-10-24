package com.aiolos.common.enums;

/**
 * @author Aiolos
 * @date 2019-10-24 14:13
 */
public enum SexEnum {

    GIRL(0),		// 女
    BOY(1),			// 男
    SECRET(2);		// 保密

    public final int value;

    SexEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
