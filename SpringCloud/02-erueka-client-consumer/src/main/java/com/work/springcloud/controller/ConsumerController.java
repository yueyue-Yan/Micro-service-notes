package com.work.springcloud.controller;

import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.work.springcloud.service.ProviderFeignService;
import com.work.springcloud.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * ClassName:ConsumerController
 * Package:com.work.springcloud.controller
 * Description: 描述信息
 *
 * @date:2023/5/7 22:16
 * @author:yueyue
 */

/**
 如何定义一个Feign的远程调用接口呢？
 1. 创建一个接口
 命名方式：xxxFeignService
 2. 该接口添加一个注解，@FeignClient(name="远程调用的微服务名称")
 将当前接口创建动态代理对象，交给Spring容器进行管理
 3. 定义提供者接口的控制器方法(从提供者方02-erueka-client-provider\src\main\java\com\work\springcloud\controller\ProviderController.java，复制粘贴过来/provider/01)
 控制器请求路径，必须一致(/provider/01)
 参数列表必须一致
 返回值必须一致 Result

 在使用Feign对象进行远程调用时，是直接使用Feign对象调用定义的方法
 执行时，会对该方法进行增强，就是发送http restful 请求操作 例如/provider/03/{id}/{username}
 知道了调用的微服务的名称: EUREKA-CLIENT-PROVIDER，知道了调用的ip地址和端口号localhost/8002
 知道了请求调用的接口地址: /provider/01
 知道了请求调用的接口地址的参数列表和返回值
 */


/**
 Feign对比RestTemplate的Put和Delete方式的操作
 RestTemplate的Put和Delete方式的请求，没有返回值，无法接收返回的参数
 Feign的Put和Delete方式的请求，是可以接收返回值的
 */


/**
 Feign对Hystrix熔断支持
 熔断器：
 一般情况下：在远程调用时，有一方报出异常，异常的信息，则会显示到浏览器当中
 使用熔断器Hystrix：可以通过熔断方法，可以将信息，自定义返回，json等等
 如果遇到超时异常，则可以快速的熔断，返回结果信息，不再阻塞在当前线程，防止线程堆积导致雪崩效应
 @FeignClient(
 name="调用的微服务名称",
 path="发送请求拼接的前缀路径",
 fallback="熔断器的字节码类型")
 1. 创建的熔断器需要实现Feign的接口实现类,被Spring管理@Component
 2. 添加Hystrix起步依赖(在02comsumer的pom文件中添加依赖，而且要在02consumer的.properties文件里配置#Feign整合Hystix的配置说明
 3. 熔断的方法，必须和调用的方法的参数列表返回值、方法名称一致
 （因为 ProviderFeignHystrix implements ProviderFeignService所以一定一致）
 出现异常时，可以迅速，找到熔断类中的方法进行返回，熔断信息

 集成Hystrix
 1. pom.xml中添加了起步依赖
 2. 引导类上添加新的注解，@EnableHystrix
 3. 控制器的方法上添加，@HystrixCommand
 fallbackMethod：服务熔断降级降级的方法名称
 参数列表和返回值必须和原方法一致
 */
@Slf4j
@RestController
@RequestMapping("/consumer")
@DefaultProperties(defaultFallback = "defaultFallbackMethod")
public class ConsumerController {

     @Autowired
     private ProviderFeignService providerFeignService;
    /**
     * @DefaultProperties(defaultFallback = "defaultFallbackMethod")
     * 公共的服务降级的方法，防止类爆炸问题
     * 能够被访问默认的降级方法，必须要求，所有访问的控制器方法的参数列表和返回值必须和默认的降级方法一致
     * 需要将使用默认降级方法的控制器上，添加一个注解@HystrixCommand
     *
     * 熔断方法优先级：
     *             1. Feign集成Hystrix(最高)-->service
     *             2. 自定义调用Hystrix熔断方法(其次)--> @HystrixCommand(fallbackMethod = "fallbackConsumer01")
     *             3. *返回默认的熔断方法(最低)-->@DefaultProperties(defaultFallback = "defaultFallbackMethod")
     *
     * */
    public Result defaultFallbackMethod(){
        return Result.error(1,"系统繁忙，请稍后再试=====默认的服务熔断降级的方法========defaultFallbackMethod");
    }

    //1. 发送get方式，无参
    @GetMapping("/01")
    //@HystrixCommand(fallbackMethod = "fallbackConsumer01")
    public Result consumer01(HttpServletRequest request, HttpServletResponse response){

        System.out.println("请求头 X-Request-Header :::>>> "+request.getHeader("X-Request-Header"));
        System.out.println("请求参数 X-Request-Parameter :::>>> "+request.getParameter("X-Request-Parameter"));
        //请求头 X-Request-Header :::>>> blueSky
        log.info("响应头 X-Response-Header : {}",response.getHeader("X-Response-Header"));

        Result result = providerFeignService.provider01();
        result.put("consumer",8002);
        return result;
    }
//    public Result fallbackConsumer01(){
//        return Result.error(1,"系统繁忙，请稍后再试=============fallbackConsumer01");
//    }

    //2. 发送get方式，restful
    @GetMapping("/02")
    @HystrixCommand
    public Result consumer02(HttpServletRequest request, HttpServletResponse response){

        System.out.println("请求头 X-Request-Header :::>>> "+request.getHeader("X-Request-Header"));
        System.out.println("请求参数 X-Request-Parameter :::>>> "+request.getParameter("X-Request-Parameter"));
        log.info("响应头 X-Response-Header : {}",response.getHeader("X-Response-Header"));

        Result result = providerFeignService.provider02(2001, "Alice");
        //为后续消费者负载均衡做铺垫
        result.put("consumer",8002);
        return result;
    }

    //3. 发送Get方式，RestFul + 键值对
    @GetMapping("/03")
    @HystrixCommand
    public Result consumer03(HttpServletRequest request, HttpServletResponse response){

        System.out.println("请求头 X-Request-Header :::>>> "+request.getHeader("X-Request-Header"));
        System.out.println("请求参数 X-Request-Parameter :::>>> "+request.getParameter("X-Request-Parameter"));
        log.info("响应头 X-Response-Header : {}",response.getHeader("X-Response-Header"));

        Result result = providerFeignService.provider03(2002, "yueyue", "22", "我是yueyue");
        //为后续消费者负载均衡做铺垫
        result.put("consumer",8002);
        return result;
    }

    //4. 发送Post方式，RestFul + 键值对 + 请求体
    @GetMapping("/04")
    @HystrixCommand
    public Result consumer04(){
        //发送Post方式，RestFul + 键值对 + 请求体
        Map<String,Object> requestBody = new HashMap<>();
        requestBody.put("loginName","Amy");
        requestBody.put("loginPwd","11111");
        Result result = providerFeignService.provider04(2003, "Amy", "23", " i am Amy", requestBody);
        //为后续消费者负载均衡做铺垫
        result.put("consumer",8002);
        return result;
    }

    //5. 发送Post方式，RestFul + 键值对 + 请求体
    @GetMapping("/05")
    @HystrixCommand
    public Result consumer05(){
        Map<String,Object> requestBody = new HashMap<>();
        requestBody.put("loginName","Smith");
        requestBody.put("loginPassword","2222");
        Result result = providerFeignService.provider05(2004, "Smith", "30", "i am Smith", requestBody);
        //为后续消费者负载均衡做铺垫
        result.put("consumer",8002);
        return result;
    }

    //6. 发送Put方式，RestFul + 键值对 + 请求体
    @GetMapping("/06")
    @HystrixCommand
    public Result consumer06(){
        Map<String,Object> requestBody = new HashMap<>();
        requestBody.put("loginName","Kitty");
        requestBody.put("loginPassword","3333");
        Result result = providerFeignService.provider06(2005, "Kitty", "21", "i am Kitty", requestBody);
        //为后续消费者负载均衡做铺垫
        result.put("consumer",8002);
        return result;
    }

    //7. 发送Delete方式，RestFul + 键值对
    @GetMapping("/07")
    @HystrixCommand
    public Result consumer07(){

        Result result = providerFeignService.provider07(2006, "Jack", "20", "i am Jack");
        //为后续消费者负载均衡做铺垫
        result.put("consumer",8002);
        return result;
    }





}