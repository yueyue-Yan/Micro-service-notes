package com.work.springcloud.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.work.springcloud.service.HystrixService;
import com.work.springcloud.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ClassName:HystrixController
 * Package:com.work.springcloud.controller
 * Description: 描述信息
 *
 * @date:2023/5/8 21:26
 * @author:yueyue
 */

@RestController
@RequestMapping("/consumer")
public class HystrixController {

    @Autowired
    private HystrixService hystrixService;

       /**
        01方法：访问时，提供者方报出除0异常，由消费者方进行服务熔断降级
        02方法：访问时，提供者方陷入阻塞状态(线程睡5秒)，然后返回结果
            消费者方就会进行服务器的熔断降级，默认Hystrix的超时熔断时间是1秒钟
            公司中，调用第三方接口时，很容易就会超过1秒钟的请求响应时间，默认1秒钟的时间太短了
            如果没有添加@HystrixCommand注解时，就会直接在浏览器界面抛出一个超时的异常
            可以通过自定义服务器熔断降级的参数来实现，自定义的时间熔断设置
        03方法：触发断路器功能
            当断路器状态为关闭时，所有请求允许访问，不论是否发生异常。
            当断路器的时间窗口周期内，发生了超过指定比例的异常时，就会将断路器的状态设置为打开的状态
                此时任何的请求，都不允许通过访问。不论当前的请求是否产生异常。
     */

    @GetMapping("/hystrix/01")
    @HystrixCommand(fallbackMethod = "fallbackMethodHystrix01")
    public Result hystrix01(){
        return hystrixService.hystrix01();
    }

    public Result fallbackMethodHystrix01(){
        return Result.error(1,"消费者工程及时熔断提供者的请求======除0========fallbackMethodHystrix01=============");
    }

    @GetMapping("/hystrix/02")
    //自定义服务熔断降级的参数设置
    @HystrixCommand(fallbackMethod = "fallbackMethodHystrix02",
                    commandProperties = {
                        //自定义服务熔断降级的时间参数设置，默认是1s，毫秒值为单位
                        @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value = "2000")
                    }
                    )
    public Result hystrix02(){
        return hystrixService.hystrix02();
    }

    public Result  fallbackMethodHystrix02(){
        return Result.error(0,"消费者工程及时熔断提供者的请求=====超时=======fallbackMethodHystrix02=============");
    }

    @GetMapping("/hystrix/03/{id}")
    @HystrixCommand(fallbackMethod = "fallbackMethodHytrix03",
                    commandProperties = {
                            //开启断路器
                            @HystrixProperty(name = "circuitBreaker.enabled", value = "true"),
                            //设置时间窗口周期，在当前的时间窗口周期内，进行统计异常比例数据
                            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds",value = "5000"),
                            //时间窗口周期内，接收的最小请求数
                            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold",value = "10"),
                            //设置异常比例，取值为0-100，代表 0% ~ 100%
                            @HystrixProperty(name= "circuitBreaker.errorThresholdPercentage",value = "50")
                    }
    )
    public Result hystrix03(@PathVariable("id") Integer id){
        return hystrixService.hystrix03(id);
    }
    /**
    当控制器中指定的熔断方法的参数列表或返回值和原方法不一致时:
    例如 public Result fallbackMethodHytrix03(){xxx} -->参数列表里没写Integer id
    报错:   FallbackDefinitionException:
            fallback method wasn't found:
                fallbackMethodHystrix03([class java.lang.Integer])
 */
    public Result fallbackMethodHytrix03(Integer id){
        return Result.error(0,"请求异常，拒绝连接=======fallbackMethodHystrix03=============");
    }


}
