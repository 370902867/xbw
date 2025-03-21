package com.bj.demo.util;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.event.ApplicationContextInitializedEvent;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * @Description:
 * @Author wangbin9
 * @Date 2024/11/22 21:30
 * Copyright  亚信科技（中国）有限公司
 */
@Configuration
public class SpringContextHolder implements ApplicationContextAware, InitializingBean, ApplicationListener<ApplicationReadyEvent> {
    // Spring应用上下文环境
    private static ApplicationContext applicationContext;
   @PostConstruct
    public void init() {
        System.out.println("——————PostConstruct");
    }
    /**
     * 实现ApplicationContextAware接口的回调方法，设置上下文环境
     *
     * @param applicationContext
     */
    public void setApplicationContext(ApplicationContext applicationContext) {
       setContext(applicationContext);
    }
    /**
     * 静态方法
     *
     * @param applicationContext
     */
    public static synchronized void setContext(ApplicationContext applicationContext) {
        System.out.println("————————SpringContextHolder.setContext执行");
        SpringContextHolder.applicationContext = applicationContext;
    }
    /**
     * @return ApplicationContext
     */
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }
    /**
     * 获取对象
     *
     * @param name
     * @return Object
     * @throws BeansException
     */
    public static Object getBean(String name) throws BeansException {
        return applicationContext.getBean(name);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("——————afterPropertiesSet");
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent ApplicationReadyEvent) {
         System.out.println("——————ApplicationReadyEvent");
    }
}
