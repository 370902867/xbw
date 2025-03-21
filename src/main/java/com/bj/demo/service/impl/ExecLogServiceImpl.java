package com.bj.demo.service.impl;

import com.bj.demo.service.LogService;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @Description:
 * @Author wangbin9
 * @Date 2024/11/22 21:42
 * Copyright  亚信科技（中国）有限公司
 */
@Component
public class ExecLogServiceImpl implements LogService, BeanPostProcessor {

    @Override
    public void insertLog(String logLevel, String className, int lineNumber, String message, String exceptionStackTrace) {
        System.out.println("执行日志");
    }

    @Override
    public String getServiceCode() {
        return "exec";
    }
}
