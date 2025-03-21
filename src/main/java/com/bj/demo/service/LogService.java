package com.bj.demo.service;

/**
 * @Description:
 * @Author wangbin9
 * @Date 2024/11/22 21:40
 * Copyright  亚信科技（中国）有限公司
 */
public interface LogService {
    void insertLog(String logLevel, String className, int lineNumber, String message, String exceptionStackTrace);

    String getServiceCode();
}
