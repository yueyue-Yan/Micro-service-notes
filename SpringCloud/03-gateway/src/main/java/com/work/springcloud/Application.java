package com.work.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import reactor.core.publisher.Mono;

@EnableEurekaClient
@SpringBootApplication
public class Application {
    @Bean
    KeyResolver userKeyResolver() {
        //浏览器输入http://localhost:5002/consumer/01--> GET "/consumer/01:java.lang.NullPointerException: value
        //改为http://localhost:5002/consumer/01?user=xxx
        return exchange -> Mono.just(exchange.getRequest().getQueryParams().getFirst("user"));
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
