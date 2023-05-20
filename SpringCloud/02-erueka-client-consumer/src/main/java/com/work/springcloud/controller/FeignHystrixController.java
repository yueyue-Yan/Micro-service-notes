package com.work.springcloud.controller;

import com.work.springcloud.service.ProviderFeignService;
import com.work.springcloud.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ClassName:FeignHystrixController
 * Package:com.work.springcloud.controller
 * Description: 描述信息
 *
 * @date:2023/5/9 11:25
 * @author:yueyue
 */

@RestController
@RequestMapping("/consumer")
public class FeignHystrixController {

    @Autowired
    private ProviderFeignService providerFeignService;

    @GetMapping("/feign/hystrix/01")
    public Result providerfh01(){
        Result result = providerFeignService.providerfh01();
        //为后续消费者负载均衡做铺垫
        result.put("consumer",8002);
        return result;

    }
}
