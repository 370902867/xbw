package com.bj.demo.starter;

import com.bj.demo.util.ConfigProperties;
import com.bj.demo.util.GlobalJucThreadPools;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.sql.SQLOutput;
import java.util.concurrent.TimeUnit;

/**
 * @Description:
 * @Author wangbin9
 * @Date 2024/11/22 21:25
 * Copyright  亚信科技（中国）有限公司
 */
@Component
public class DemoCommandLine implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        System.out.println("——————————————CommandLineRunner："+ConfigProperties.getProperty("test.name"));
        for (int i = 0; i < 100; i++){
            int finalI = i;
            GlobalJucThreadPools.getLogThreadPool().execute(() -> {
            System.out.println("Thread-CommandLineRunner"+Thread.currentThread().getName()+":"+ finalI);
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        }

    }
}
