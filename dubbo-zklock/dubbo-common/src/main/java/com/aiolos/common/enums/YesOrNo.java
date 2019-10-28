package com.aiolos.common.enums;

/**
 * @author Aiolos
 * @date 2019-10-24 14:13
 */
public enum YesOrNo {

    YES(1),			// 是	有错误
    NO(0);			// 否	无错误

    public final int value;

    YesOrNo(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
