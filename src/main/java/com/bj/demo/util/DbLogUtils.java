package com.bj.demo.util;

import com.bj.demo.service.LogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Collections;
import java.util.List;

/**
 * @Description:
 * @Author wangbin9
 * @Date 2024/11/22 21:36
 * Copyright  亚信科技（中国）有限公司
 */
@Component
public class DbLogUtils {
    private static final Logger logger = LoggerFactory.getLogger(DbLogUtils.class);
    private static List<LogService> logServiceList;
    @Autowired
    public DbLogUtils(List<LogService> logServiceList) {
        DbLogUtils.logServiceList = Collections.synchronizedList(logServiceList);
    }

    public static void info(String logType, String message) {
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        StackTraceElement caller = stackTraceElements[2]; // 第一个元素是 getStackTrace 本身，第二个是 logInfo 方法，第三个是调用者
        logger.info("{}:{} - {}", caller.getClassName(), caller.getLineNumber(), message);
        insertLog(logType, "INFO", caller.getClassName(), caller.getLineNumber(), message, null);
    }

    public static void error(String logType, String message, Throwable e) {
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        StackTraceElement caller = stackTraceElements[2]; // 第一个元素是 getStackTrace 本身，第二个是 logError 方法，第三个是调用者
        logger.error("{}:{} - {}", caller.getClassName(), caller.getLineNumber(), message, e);
        insertLog(logType, "ERROR", caller.getClassName(), caller.getLineNumber(), message, e);
    }

    private static void insertLog(String logType, String logLevel, String className, int lineNumber, String message, Throwable e) {
        String exceptionStackTrace = e != null ? getStackTrace(e) : null;
        LogService logService = getLogService(logType);
        if (logService != null) {
            logService.insertLog(logLevel, className, lineNumber, message, exceptionStackTrace);
        } else {
            logger.error("No log service found for log type: {}", logType);
        }
    }

    private static LogService getLogService(String logType) {
        return logServiceList.stream()
                .filter(service -> service.getServiceCode().equals(logType))
                .findFirst()
                .orElse(null);
    }

    private static String getStackTrace(Throwable e) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        return sw.toString();
    }
}
