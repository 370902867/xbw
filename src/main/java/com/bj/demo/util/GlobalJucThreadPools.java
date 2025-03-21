package com.bj.demo.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class GlobalJucThreadPools {
    private static final Logger log = LoggerFactory.getLogger(GlobalJucThreadPools.class);

    // 私有构造函数防止外部创建
    private GlobalJucThreadPools() {}
    private static int SHOW_INFO_INTERVAL;
    static {
        SHOW_INFO_INTERVAL = ConfigProperties.getProperty("global_thread_pool.show_info_interval", Integer.class, 3);
    }

    // **********1、日志处理线程池start**********
    // 静态内部类，用于日志处理线程池的单例实现，全局只有一个实例
    private static class LogThreadPoolHolder {
        private static final String LOG_THREAD_POOL_PREFIX = "LogThreadPool-";
        private static final String LOG_THREAD_POOL_CORE_SIZE = "log_thread_pool.core_size";
        private static final String LOG_THREAD_POOL_MAX_SIZE = "log_thread_pool.max_size";
        private static final String LOG_THREAD_POOL_QUEUE_SIZE = "log_thread_pool.queue_size";
        private static final String LOG_THREAD_POOL_KEEP_ALIVE = "log_thread_pool.keep_alive";
        // 默认值，可通过配置文件或环境变量修改
        private static final int DEF_LOG_THREAD_POOL_CORE_SIZE = 1;
        private static final int DEF_LOG_THREAD_POOL_MAX_SIZE = 5;
        private static final int DEF_LOG_THREAD_POOL_QUEUE_SIZE = 1000;
        private static final long DEF_LOG_THREAD_KEEP_ALIVE = 60;

        private static final ThreadPoolExecutor INSTANCE;

        static {
            try {
                INSTANCE = createThreadPool(LOG_THREAD_POOL_PREFIX,
                        ConfigProperties.getProperty(LOG_THREAD_POOL_CORE_SIZE, Integer.class,DEF_LOG_THREAD_POOL_CORE_SIZE),
                        ConfigProperties.getProperty(LOG_THREAD_POOL_MAX_SIZE, Integer.class,DEF_LOG_THREAD_POOL_MAX_SIZE),
                        ConfigProperties.getProperty(LOG_THREAD_POOL_KEEP_ALIVE, Long.class,DEF_LOG_THREAD_KEEP_ALIVE),
                        TimeUnit.SECONDS,
                        new LinkedBlockingQueue<>(ConfigProperties.getProperty(LOG_THREAD_POOL_QUEUE_SIZE, Integer.class,DEF_LOG_THREAD_POOL_QUEUE_SIZE)));
            } catch (IllegalArgumentException e) {
                log.error("创建日志处理线程池失败: {}", e.getMessage());
                throw new RuntimeException("无法创建日志处理线程池", e);
            }
        }
    }

    // 获取日志处理线程池实例
    public static ThreadPoolExecutor getLogThreadPool() {
        return LogThreadPoolHolder.INSTANCE;
    }
    // **********1、日志处理线程池end**********


    // 创建线程池的通用方法，添加参数校验逻辑
    private static ThreadPoolExecutor createThreadPool(String prefix, int corePoolSize,
                                                       int maxPoolSize, long keepAliveTime,
                                                       TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        if (corePoolSize > maxPoolSize) {
            throw new IllegalArgumentException("核心线程数不能大于最大线程数");
        }

        return new ShowInfoThreadPoolExecutor(
                corePoolSize,
                maxPoolSize,
                keepAliveTime,
                unit,
                workQueue,
                new NamedThreadFactory(prefix),
                new ThreadPoolExecutor.AbortPolicy(),
                prefix
        );
    }

    // 线程工厂，为线程命名
    private static class NamedThreadFactory implements ThreadFactory {
        private final String namePrefix;
        private int threadId;

        NamedThreadFactory(String namePrefix) {
            this.namePrefix = namePrefix;
        }

        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(r, namePrefix + threadId++);
            // t.setDaemon(true); // 根据需要设置为守护线程
            return t;
        }
    }

    private static class ShowInfoThreadPoolExecutor extends ThreadPoolExecutor {
        private final String namePrefix;
        private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        private final Runnable infoTask = this::showInfo;

        public ShowInfoThreadPoolExecutor(int corePoolSize,
                                          int maximumPoolSize,
                                          long keepAliveTime,
                                          TimeUnit unit,
                                          BlockingQueue<Runnable> workQueue,
                                          ThreadFactory threadFactory,
                                          RejectedExecutionHandler handler,
                                          String namePrefix) {
            super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
            this.namePrefix = namePrefix;
            this.scheduler.scheduleAtFixedRate(infoTask, SHOW_INFO_INTERVAL, SHOW_INFO_INTERVAL, TimeUnit.SECONDS);
        }

        private void showInfo() {
            //打印线程池信息:核心线程数、最大线程数、当前线程数、活动线程数、队列中等任务数、已完成任务数
            log.info("打印线程池信息-{}：核心线程数={},最大线程数={},当前线程数={}, 活动线程数={},队列中等任务数={}, 已完成任务数={}",
                    namePrefix,getCorePoolSize(),getMaximumPoolSize(),getPoolSize(), getActiveCount(), getQueue().size(), getCompletedTaskCount());
        }

        @Override
        public void execute(Runnable command) {
            super.execute(command);
        }

        @Override
        public void shutdown() {
            scheduler.shutdown();
            super.shutdown();
        }
    }
}