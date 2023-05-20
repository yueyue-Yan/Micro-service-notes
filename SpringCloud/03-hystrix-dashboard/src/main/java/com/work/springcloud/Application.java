package com.work.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

@SpringBootApplication
//开启eureka客户端
@EnableEurekaClient
//开启hystrix仪表盘
@EnableHystrixDashboard
//http://localhost:9001/hystrix
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
