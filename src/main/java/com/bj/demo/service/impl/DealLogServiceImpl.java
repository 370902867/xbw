package com.bj.demo.service.impl;

import com.bj.demo.service.LogService;
import org.springframework.stereotype.Component;

import java.sql.SQLOutput;

/**
 * @Description:
 * @Author wangbin9
 * @Date 2024/11/22 21:43
 * Copyright  亚信科技（中国）有限公司
 */
@Component
public class DealLogServiceImpl implements LogService {
    @Override
    public void insertLog(String logLevel, String className, int lineNumber, String message, String exceptionStackTrace) {
        System.out.println("deal log");
    }

    @Override
    public String getServiceCode() {
        return "deal";
    }
}
