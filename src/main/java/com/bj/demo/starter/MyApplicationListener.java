package com.bj.demo.starter;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * @Description:
 * @Author wangbin9
 * @Date 2024/11/23 08:44
 * Copyright  亚信科技（中国）有限公司
 */
@Component
public class MyApplicationListener implements ApplicationListener<ContextRefreshedEvent> {
    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        System.out.println("——————————MyApplicationListener.contextRefreshedEvent");
    }
}
