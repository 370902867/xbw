package com.bj.demo.controller;

import com.bj.demo.util.ConfigProperties;
import com.bj.demo.util.DbLogUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description:
 * @Author wangbin9
 * @Date 2024/11/22 21:13
 * Copyright  亚信科技（中国）有限公司
 */
@RestController
public class TestController {
    // 测试接口
    @RequestMapping("/test")
    public String test(){
        System.out.println(ConfigProperties.getProperty("test.name"));
        DbLogUtils.info("deal", "test");
        return "hello world";
    }
}
