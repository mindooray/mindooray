package com.nhnacademy.team4.mindooray.utils;

import org.springframework.context.ApplicationContext;

public final class BeanUtils {
    private static ApplicationContext applicationContext;
    private BeanUtils() {}

    public static void init(ApplicationContext context) {
        applicationContext = context;
    }

    public static <T> T getBean(Class<T> clz) {
        return applicationContext.getBean(clz);
    }
}
