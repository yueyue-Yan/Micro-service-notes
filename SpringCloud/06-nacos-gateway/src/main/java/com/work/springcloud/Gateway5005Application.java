package com.work.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class Gateway5005Application {

    public static void main(String[] args) {
        SpringApplication.run(Gateway5005Application.class, args);
    }

}
