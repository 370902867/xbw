package com.bj;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

import java.util.Properties;

@SpringBootApplication
@ServletComponentScan
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(DemoApplication.class);
        Properties defaultProperties = new  Properties();
        defaultProperties.put("test.name", "8081");
        application.setDefaultProperties(defaultProperties);
        application.run(args);
    }

}
