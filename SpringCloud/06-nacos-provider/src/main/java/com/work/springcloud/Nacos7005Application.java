package com.work.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class Nacos7005Application {

    public static void main(String[] args) {
        SpringApplication.run(Nacos7005Application.class, args);
    }

}
