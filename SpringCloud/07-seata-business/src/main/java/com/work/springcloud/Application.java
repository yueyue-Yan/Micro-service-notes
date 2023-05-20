package com.work.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableDiscoveryClient
@EnableFeignClients
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
    //ConfigurableApplicationContext run = SpringApplication.run(BusinessApplication.class, args);
    //调用微服务
    //BusinessService bean = run.getBean(BusinessService.class);
    //调用采购方法
    //bean.purchase("1","2",1);
    //System.out.println("采购完成...");

}
