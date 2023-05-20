package com.work.springcloud.service;

import com.work.springcloud.utils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * ClassName:HystrixService
 * Package:com.work.springcloud.service
 * Description: 描述信息
 *
 * @date:2023/5/8 21:29
 * @author:yueyue
 */

@FeignClient(name="EUREKA-CLIENT-PROVIDER",path = "/provider")
public interface HystrixService {

    /**Feign集成Hystrix的测试方法*/
    @GetMapping("/feign/hystrix/01")
    public Result Providerfh01();

    /**Hystrix测试报出异常的方法*/
    @GetMapping("/hystrix/01")
    public Result hystrix01();

    /**Hystrix测试报出超时异常的方法*/
    @GetMapping("/hystrix/02")
    public Result hystrix02();

    /**Hystrix测试断路器的方法*/
    @GetMapping("/hystrix/03/{id}")
    public Result hystrix03(@PathVariable("id") Integer id);

}
