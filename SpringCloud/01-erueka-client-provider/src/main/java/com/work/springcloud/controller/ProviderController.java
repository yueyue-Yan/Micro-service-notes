package com.work.springcloud.controller;

import com.work.springcloud.utils.Result;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * ClassName:ProviderController
 * Package:com.work.springcloud.controller
 * Description: 描述信息
 *
 * @date:2023/5/8 10:07
 * @author:yueyue
 */


/**
 远程访问的参数发送的方式
 http://localhost:7001/provider/01
 这种方式，直接接收即可
 http://localhost:7001/provider/01/{id}/{username}
 通过地址栏参数，进行封装
 http://localhost:7001/provider/01/{id}/{username}?age=18&desc=我是Alice
 通过地址栏传递键值对参数
 http://localhost:7001/provider/01/{id}/{username}?age=18&desc=我是Alice
 将部分参数，封装到了请求体当中
 远程访问的参数的接收方式
 http://localhost:7001/provider/01/{id}/{username}
 通过注解进行接收，@PathVariable(name="username")
 http://localhost:7001/provider/01/{id}/{username}?age=18&desc=我是Alice
 通过注解进行接收，@PathVariable(name="id")   例参数：@PathVariable("id") Integer id
 通过注解接收键值对的参数，@RequestParams(name="age")
 http://localhost:7001/provider/01/{id}/{username}?age=18&desc=我是Alice
 通过注解进行接收，@PathVariable(name="id")
 通过注解接收键值对的参数，@RequestParams(name="age")
 通过注解接收 请求体 中的数据，@RequestBody
 例子：

 @PutMapping("/06/{id}/{username}")
 public R provider06(@PathVariable Integer id,
 @PathVariable String username,
 @RequestParam String age,
 @RequestParam String desc,
 @RequestBody  Map<String,Object> requestBody){}

 */
@RestController
@RequestMapping("/provider")
public class ProviderController {


    @GetMapping("/01")
    public Result provider01(){
        //测试熔断
        //int i = 1/0; //http://localhost:8002/consumer/01
        return Result.success(0,"远程调用成功");
    }

    @GetMapping("/02/{id}/{username}")
    public Result provider02(@PathVariable("id") Integer id,@PathVariable("username") String username){
        Map<String,Object> providerData = new HashMap<>();
        //测试熔断
        //int i = 1/0;
        providerData.put("id",id);
        providerData.put("username",username);
        return Result.success(0,"远程调用成功",providerData);
    }

    @GetMapping("/03/{id}/{username}")
    public Result provider03(@PathVariable("id") Integer id,
                             @PathVariable("username") String username,
                             @RequestParam("age") String age,
                             @RequestParam("desc") String desc
    ){
        Map<String,Object> providerData = new HashMap<>();
        providerData.put("id",id);
        providerData.put("username",username);
        providerData.put("age",age);
        providerData.put("desc",desc);
        return Result.success(0,"远程调用成功",providerData);
    }


    /**post*/
    @PostMapping("/04/{id}/{username}")
    public Result provider04(@PathVariable("id") Integer id,
                             @PathVariable("username") String username,
                             @RequestParam("age") String age,
                             @RequestParam("desc") String desc,
                             @RequestBody  Map<String,Object> requestBody
    ){
        Map<String,Object> providerData = new HashMap<>();
        providerData.put("id",id);
        providerData.put("username",username);
        providerData.put("age",age);
        providerData.put("desc",desc);
        providerData.put("requestBody",requestBody);
        return Result.success(0,"远程调用成功~",providerData);
    }

    /**post*/
    @PostMapping("/05/{id}/{username}")
    public Result provider05(@PathVariable("id") Integer id,
                             @PathVariable("username") String username,
                             @RequestParam("age") String age,
                             @RequestParam("desc") String desc,
                             @RequestBody  Map<String,Object> requestBody
    ){
        Map<String,Object> providerData = new HashMap<>();

        providerData.put("id",id);
        providerData.put("username",username);
        providerData.put("age",age);
        providerData.put("desc",desc);
        providerData.put("requestBody",requestBody);

        return Result.success(0,"远程调用成功!",providerData);
    }

    /**put*/
    @PutMapping("/06/{id}/{username}")
    public Result provider06(@PathVariable("id") Integer id,
                             @PathVariable("username") String username,
                             @RequestParam("age") String age,
                             @RequestParam("desc") String desc,
                             @RequestBody  Map<String,Object> requestBody
    ){
        Map<String,Object> providerData = new HashMap<>();

        providerData.put("id",id);
        providerData.put("username",username);
        providerData.put("age",age);
        providerData.put("desc",desc);
        providerData.put("requestBody",requestBody);

        System.out.println("id :::>>> "+id);
        System.out.println("username :::>>> "+username);
        System.out.println("age :::>>> "+age);
        System.out.println("desc :::>>> "+desc);
        System.out.println("requestBody :::>>> "+requestBody);

        return Result.success(0,"远程调用成功!",providerData);
    }

    /**delete*/
    @DeleteMapping("/07/{id}/{username}")
    public Result provider07(@PathVariable("id") Integer id,
                             @PathVariable("username") String username,
                             @RequestParam("age") String age,
                             @RequestParam("desc") String desc
    ){
        Map<String,Object> providerData = new HashMap<>();

        providerData.put("id",id);
        providerData.put("username",username);
        providerData.put("age",age);
        providerData.put("desc",desc);

        System.out.println("id :::>>> "+id);
        System.out.println("username :::>>> "+username);
        System.out.println("age :::>>> "+age);
        System.out.println("desc :::>>> "+desc);

        return Result.success(0,"远程调用成功!",providerData);
    }


}
