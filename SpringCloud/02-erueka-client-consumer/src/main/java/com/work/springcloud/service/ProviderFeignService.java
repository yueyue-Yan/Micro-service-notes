package com.work.springcloud.service;

import com.work.springcloud.utils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * ClassName:ProviderFeignService
 * Package:com.work.springcloud.service
 * Description: 描述信息
 *
 * @date:2023/5/8 15:24
 * @author:yueyue
 */

@FeignClient(name="EUREKA-CLIENT-PROVIDER",
        path = "/provider",
        fallback = ProviderFeignHystrixImpl.class //因为ConsumerController里已经写了 @HystrixCommand(fallbackMethod = "fallbackConsumer01")
)
public interface ProviderFeignService {

    //必须一致（下同）
    //从提供者方:
    // 02-erueka-client-provider的controller\ProviderController.java，复制粘贴过来/provider/01  方法名provider01可以不一致，其他必须全部相同
    @GetMapping("/01")
    public Result provider01();

    //以restful方式进行传递
    @GetMapping("/02/{id}/{username}")
    public Result provider02(@PathVariable("id") Integer id, @PathVariable("username") String username);

    @GetMapping("/03/{id}/{username}")
    public Result provider03(@PathVariable("id") Integer id,
                             @PathVariable("username") String username,
                             @RequestParam("age") String age,
                             @RequestParam("desc") String desc);

    @PostMapping("/04/{id}/{username}")
    public Result provider04(@PathVariable("id") Integer id,
                             @PathVariable("username") String username,
                             @RequestParam("age") String age,
                             @RequestParam("desc") String desc,
                             @RequestBody Map<String,Object> requestBody);

    @PostMapping("/05/{id}/{username}")
    public Result provider05(@PathVariable("id") Integer id,
                             @PathVariable("username") String username,
                             @RequestParam("age") String age,
                             @RequestParam("desc") String desc,
                             @RequestBody  Map<String,Object> requestBody);

    @PutMapping("/06/{id}/{username}")
    public Result provider06(@PathVariable("id") Integer id,
                             @PathVariable("username") String username,
                             @RequestParam("age") String age,
                             @RequestParam("desc") String desc,
                             @RequestBody  Map<String,Object> requestBody);

    @DeleteMapping("/07/{id}/{username}")
    public Result provider07(@PathVariable("id") Integer id,
                             @PathVariable("username") String username,
                             @RequestParam("age") String age,
                             @RequestParam("desc") String desc);
    @RequestMapping("/feign/hystrix/01")
   // /provider/feign/hystrix/01
    public Result providerfh01();

}
