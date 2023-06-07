package com.nhnacademy.team4.mindooray.utils;

import org.springframework.context.ApplicationContext;

public final class BeanUtils {
    private BeanUtils() {}

    public static <T> T getBean(Class<T> clz) {
        ApplicationContext context = ApplicationContextProvider.getApplicationContext();
        return context.getBean(clz);
    }
}
