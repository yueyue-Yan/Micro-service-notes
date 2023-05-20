package com.work.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.web.client.RestTemplate;

//开启SpringSession操作
@EnableRedisHttpSession
//开启注册中心客户端
@EnableEurekaClient
@SpringBootApplication
public class Application {
    @Bean
    @LoadBalanced //RestTemplate负载均衡
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
