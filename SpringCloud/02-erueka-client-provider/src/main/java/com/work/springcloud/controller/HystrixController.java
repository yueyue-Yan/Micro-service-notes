package com.work.springcloud.controller;

import com.work.springcloud.utils.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ClassName:HystrixController
 * Package:com.work.springcloud.controller
 * Description: 描述信息
 *
 * @date:2023/5/8 21:34
 * @author:yueyue
 */
@RestController
@RequestMapping("/provider/hystrix")
public class HystrixController {

    /**模拟运行时异常*/
    @GetMapping("/01")
    public Result hystrix01(){
        int i=1/0;
        return Result.success();
    }

    /**模拟超时异常*/
    @GetMapping("/02")
    public Result hystrix02() throws InterruptedException {
        Thread.sleep(5000);
        return Result.success();
    }
    /**断路器*/
    @GetMapping("/03/{id}")
    public Result hystrix03(@PathVariable Integer id){

        if(id <= 0){
            int i=1/0;
            return Result.error(1,"报错了...");
        }

        return Result.success();

    }
}