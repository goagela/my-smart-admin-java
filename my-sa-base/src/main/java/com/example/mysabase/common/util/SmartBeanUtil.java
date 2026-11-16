package com.example.mysabase.common.util;

import org.springframework.beans.BeanUtils;

public class SmartBeanUtil {

    public static<T> T copy(Object source,Class<T> target){
        if (source == null || target == null) {
            return null;
        }
        try {
            //例如传入的target为RequestEmployee.class，则下面这句等价于 RequestEmployee newInstance = new RequestEmployee();
            T newInstance = target.getDeclaredConstructor().newInstance();

            BeanUtils.copyProperties(source,newInstance);
            return newInstance;
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
