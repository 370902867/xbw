package com.bj.demo.starter;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.stereotype.Component;

/**
 * @Description:
 * @Author wangbin9
 * @Date 2024/11/23 08:44
 * Copyright  亚信科技（中国）有限公司
 */
@Component
public class MyApplicationListener2 implements ApplicationListener<ContextStartedEvent> {
    @Override
    public void onApplicationEvent(ContextStartedEvent contextStartedEvent) {
        System.out.println("——————————MyApplicationListener.contextStartedEvent");
    }
}
