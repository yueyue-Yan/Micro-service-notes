package com.work.springcloud.controller;

import com.work.springcloud.utils.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ClassName:FeignHystrixController
 * Package:com.work.springcloud.controller
 * Description: 描述信息
 *
 * @date:2023/5/8 21:18
 * @author:yueyue
 */

@RestController
@RequestMapping("/provider/feign/hystrix")
public class FeignHystrixController {

    @GetMapping("/01")
    public Result providerfh01(){
        //测试熔断
        int i = 1/0; //http://localhost:8002//consumer/feign/hystrix/01
        return Result.success(0,"远程调用成功");
    }

}
