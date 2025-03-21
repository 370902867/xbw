package com.bj.demo.util;

import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * @Description:
 * @Author wangbin9
 * @Date 2024/8/22 13:58
 * Copyright  亚信科技（中国）有限公司
 */
@Component
public class ConfigProperties implements EnvironmentAware {
    //静态全局唯一，Springboot启动的时候会调用setEnvironment进行初始化
    private static Environment environment;

    @Override
    public void setEnvironment(Environment environment) {
        ConfigProperties.environment = environment;
    }

    public static String getProperty(String key) {
        return environment.getProperty(key);
    }
    public static String getProperty(String key, String defaultValue) {
        return environment.getProperty(key, defaultValue);
    }
    public static <T> T getProperty(String key, Class<T> targetType) {
        return environment.getProperty(key, targetType);
    }
    public static <T> T getProperty(String key, Class<T> targetType, T defaultValue) {
        return environment.getProperty(key, targetType, defaultValue);
    }

}
