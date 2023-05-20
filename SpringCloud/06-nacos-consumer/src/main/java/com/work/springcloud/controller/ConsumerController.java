package com.work.springcloud.controller;

import com.work.springcloud.feignService.ProviderFeignService;
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
 * @date:2023/5/13 15:19
 * @author:yueyue
 */
@Slf4j
@RestController
@RequestMapping("/consumer")
public class ConsumerController {

    @Autowired
    private ProviderFeignService service;

    @GetMapping("/01")
    public Result consumer01(HttpServletRequest request) throws InterruptedException {
        System.out.println("请求头 X-Request-Header :::>>> "+request.getHeader("X-Request-Header"));
        //发送Get方式，无参
        Result r = service.provider01();
        r.put("consumer",8005);
       // Thread.sleep(2000);
        return r;
    }


    @GetMapping("/02")
    public Result consumer02(HttpServletRequest request, HttpServletResponse response){

        System.out.println("请求头 X-Request-Header :::>>> "+request.getHeader("X-Request-Header"));
        System.out.println("请求头 bbb :::>>> "+request.getParameter("bbb"));
        log.info("响应头 ccc : {}",response.getHeader("bbb"));

        //发送GetResult方式，RestFul
        Result r = service.provider02(111, "张3");
        r.put("consumer",8005);
      //  int i=1/0;
        return r;
    }

    @GetMapping("/03")
    public Result consumer03(){

        //发送Get方式，RestFul + 键值对
        Result r = service.provider03(222, "李4", "33", "我是李4");
        r.put("consumer",8005);
        return r;
    }

    @GetMapping("/04")
    public Result consumer04(){
        //发送Post方式，RestFul + 键值对 + 请求体
        Map<String,Object> requestBody = new HashMap<>();
        requestBody.put("loginName","wangwu");
        requestBody.put("loginPwd","123456");

        Result r = service.provider04(333, "王5", "44", "我是王5", requestBody);
        r.put("consumer",8005);
        return r;
    }

    @GetMapping("/05")
    public Result consumer05(){
        //发送Post方式，RestFul + 键值对 + 请求体
        Map<String,Object> requestBody = new HashMap<>();
        requestBody.put("loginName","ZhaoLiu");
        requestBody.put("loginPwd","123123");

        Result r = service.provider04(444, "赵6", "55", "我是赵6", requestBody);
        r.put("consumer",8005);
        return r;
    }

    @GetMapping("/06")
    public Result consumer06(){
        //发送Put方式，RestFul + 键值对 + 请求体
        Map<String,Object> requestBody = new HashMap<>();
        requestBody.put("loginName","TianQi");
        requestBody.put("loginPwd","321321");

        Result r = service.provider06(555, "田7", "66", "我是田7", requestBody);
        r.put("consumer",8005);
        return r;
    }

    @GetMapping("/07")
    public Result consumer07(){
        //发送Delete方式，RestFul + 键值对
        Result r = service.provider07(666, "钱8", "77", "我是钱8");
        r.put("consumer",8005);
        return r;
    }

}

