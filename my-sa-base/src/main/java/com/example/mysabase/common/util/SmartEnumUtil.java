package com.example.mysabase.common.util;

import com.example.mysabase.common.enumeration.BaseEnum;

import java.util.stream.Stream;

public class SmartEnumUtil {

    /**
     * 类似于枚举的valueOf方法
     * @param value
     * @param enumClass
     * @return
     * @param <T>
     */
    public static <T extends BaseEnum> T getEnumByValue(Object value, Class<T> enumClass) {
        if (null == value) {
            return null;
        }
        return Stream.of(enumClass.getEnumConstants())
                .filter(e -> e.equalsValue(value))
                .findFirst()
                .orElse(null);
    }
}
