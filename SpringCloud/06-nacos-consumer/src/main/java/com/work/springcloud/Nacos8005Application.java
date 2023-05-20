package com.work.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

//区别
//@EnableEurekaClient = 只能注册服务到Eureka的配置中心中
//@EnableDiscoveryClient = 可以注册服务到 Eureka 、 Zookeeper 、 Nacos 、 Consul等配置中心

@EnableDiscoveryClient
@EnableFeignClients
@SpringBootApplication

public class Nacos8005Application {

    public static void main(String[] args) {
        SpringApplication.run(Nacos8005Application.class, args);
    }

}
