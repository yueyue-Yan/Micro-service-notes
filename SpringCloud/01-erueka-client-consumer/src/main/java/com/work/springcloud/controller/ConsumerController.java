package com.work.springcloud.controller;

import com.work.springcloud.beans.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

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
 RestTemplate：Spring封装的远程调用的模板对象，发送http的restful api请求操作
 post、delete、put、get：不同的请求方式，代表不同的增删改查
 方法名称
 getForEntity(String url,Class responseType)：返回值ResponseEntity<T>
 getForEntity(String url,Class responseType,Object... objs)：返回值ResponseEntity<T>
 getForEntity(String url,Class responseType,Map params)：返回值ResponseEntity<T>
 参数列表：
 参数1，url请求地址
 参数2，响应的字节码类型
 参数3，封装的参数，可以用可变参数封装，也可以通过Map集合的键值对进行封装
 返回值：ResponseEntity
 获取提供者返回的结果
 获取提供者返回的响应头
 获取提供者返回的响应状态码
 获取提供者返回的响应状态码对应的值

 getForObject(String url,Class responseType)：返回值<T>
 getForObject(String url,Class responseType,Object... objs)：返回值<T>
 getForObject(String url,Class responseType,Map params)：返回值<T>
 参数列表：
 参数1，url请求地址
 参数2，响应的字节码类型
 参数3，封装的参数，可以用可变参数封装，也可以通过Map集合的键值对进行封装
 返回值：T
 提供者返回的具体数据

 postForEntity(String url,Object request,Class responseType)：返回值ResponseEntity<T>
 postForEntity(String url,Object request,Class responseType,Object... objs)：返回值ResponseEntity<T>
 postForEntity(String url,Object request,Class responseType,Map params)：返回值ResponseEntity<T>
 参数列表：
 参数1，url请求地址
 参数2，封装到请求体中的数据
 参数3，响应的字节码类型
 参数4，封装的参数，可以用可变参数封装，也可以通过Map集合的键值对进行封装
 返回值：ResponseEntity
 获取提供者返回的结果
 获取提供者返回的响应头
 获取提供者返回的响应状态码
 获取提供者返回的响应状态码对应的值
 postForObject：返回值<T>
 postForObject(String url,Object request,Class responseType)：返回值<T>
 postForObject(String url,Object request,Class responseType,Object... objs)：返回值<T>
 postForObject(String url,Object request,Class responseType,Map params)：返回值<T>
 参数列表：
 参数1，url请求地址
 参数2，封装到请求体中的数据
 参数3，响应的字节码类型
 参数4，封装的参数，可以用可变参数封装，也可以通过Map集合的键值对进行封装
 返回值：T
 提供者返回的具体数据

 put(String url,Object request,Class responseType)：返回值void，如果进行修改，想要获取提供者的返回值，请使用post方式
 put(String url,Object request,Object... objs)：返回值void，如果进行修改，想要获取提供者的返回值，请使用post方式
 put(String url,Object request,Map params)：返回值void，如果进行修改，想要获取提供者的返回值，请使用post方式
 参数列表：
 参数1，url请求地址
 参数2，封装到请求体中的数据
 参数3，封装的参数，可以用可变参数封装，也可以通过Map集合的键值对进行封装

 delete(String url)：返回值void，如果进行删除，想要获取提供者的返回值，请使用get方式
 delete(String url,Object... objs)：返回值void，如果进行删除，想要获取提供者的返回值，请使用get方式
 delete(String url,Map params)：返回值void，如果进行删除，想要获取提供者的返回值，请使用get方式
 参数列表：
 参数1，url请求地址
 参数2，封装的参数，可以用可变参数封装，也可以通过Map集合的键值对进行封装
 */


/**
 负载均衡调用
 R          estTemplate 负载均衡
 (1)将url地址中的ip+端口，改为微服务的名称，进行远程调用：http://localhost:7001/provider/01-->http://EUREKA-CLIENT-PROVIDER/provider/01
 UnknownHostException: EUREKA-CLIENT-PROVIDER
 EUREKA-CLIENT-PROVIDER代表：7001、7002两个微服务,不知道找哪一个
 (2)在RestTemplate初始化的地方(即引导类Application)，添加一个负载均衡的注解，@LoadBalanced
 添加了该注解，RestTemplate即可支持负载均衡的远程调用
 */
@Slf4j
@RestController
@RequestMapping("/consumer")
public class ConsumerController {

    @Autowired
    private RestTemplate restTemplate;

/**
 * 消费者工程，都是get方式接收
 * 通过不同的restTemplate的请求发送，在提供者进行get/post/put/delete方式的接收
 * 这种方式方便消费者进行测试，也可以通过postMan进行测试
 */
    @GetMapping("/01")
    public Result consumer01(HttpServletRequest request, HttpServletResponse response){
        //访问提供者地址
        //String url = "http://localhost:7001/provider/01";
        //RestTemplate 负载均衡
        String url = "http://EUREKA-CLIENT-PROVIDER/provider/01";
        ResponseEntity<Result> entity = restTemplate.getForEntity(url, Result.class);

        System.out.println("请求头 X-Request-Header :::>>> "+request.getHeader("X-Request-Header"));
        System.out.println("请求参数 X-Request-Parameter :::>>> "+request.getParameter("X-Request-Parameter"));

        log.info("响应头 X-Response-Header : {}",response.getHeader("X-Response-Header"));

        System.out.println("提供者返回的响应头 :::>>> " + entity.getHeaders());
        System.out.println("提供者返回的响应结果 :::>>> " + entity.getBody());
        System.out.println("提供者返回的响应状态码 :::>>> " + entity.getStatusCode());
        System.out.println("提供者返回的响应状态码值 :::>>> " + entity.getStatusCodeValue());
        Result result = entity.getBody();

        //为后续的负载均衡做铺垫
        result.put("consumer",8001);

        return result;
    }

    @GetMapping("/02")
    public Result consumer02(HttpServletRequest request, HttpServletResponse response){

        System.out.println("请求头 X-Request-Header :::>>> "+request.getHeader("X-Request-Header"));
        System.out.println("请求参数 X-Request-Parameter :::>>> "+request.getParameter("X-Request-Parameter"));
        log.info("响应头 X-Response-Header : {}",response.getHeader("X-Response-Header"));

        //String url = "http://localhost:7001/provider/02/{id}/{username}";
        String url = "http://EUREKA-CLIENT-PROVIDER/provider/02/{id}/{username}";
        Map<String,Object> param = new HashMap<>();
        param.put("id",1001);
        param.put("username","Alice");
        Result result = restTemplate.getForObject(url, Result.class, param);

        //为后续的负载均衡做铺垫
        result.put("consumer",8001);

        return result;

    }

    @GetMapping("/03")
    public Result consumer03(HttpServletRequest request, HttpServletResponse response){

        System.out.println("请求头 X-Request-Header :::>>> "+request.getHeader("X-Request-Header"));
        System.out.println("请求参数 X-Request-Parameter :::>>> "+request.getParameter("X-Request-Parameter"));
        log.info("响应头 X-Response-Header : {}",response.getHeader("X-Response-Header"));

        //String url = "http://localhost:7001/provider/03/{id}/{username}?age=22&desc=我是yueyue";
        String url = "http://EUREKA-CLIENT-PROVIDER/provider/03/{id}/{username}?age=22&desc=我是yueyue";
        Map<String,Object> param = new HashMap<>();
        param.put("id",1002);
        param.put("username","yueyue");
        Result result = restTemplate.getForObject(url, Result.class, param);

        //为后续的负载均衡做铺垫
        result.put("consumer",8001);

        return result;

    }

    @GetMapping("/04")
    public Result consumer04(){
        //访问提供者地址
        //String url = "http://localhost:7001/provider/04/{id}/{username}?age=20&desc=我是Amy";
        String url = "http://EUREKA-CLIENT-PROVIDER/provider/04/{id}/{username}?age=20&desc=我是Amy";


        Map<String,Object> request = new HashMap<>();
        Map<String,Object> paramsMap = new HashMap<>();

        request.put("loginName","Amy");
        request.put("loginPassword","1111");

        paramsMap.put("id",1003);
        paramsMap.put("username","Amy");
        /**post*/
        ResponseEntity<Result> entity = restTemplate.postForEntity(url, request, Result.class, paramsMap);

        System.out.println("提供者返回的响应头 :::>>> " + entity.getHeaders());
        System.out.println("提供者返回的响应结果 :::>>> " + entity.getBody());
        System.out.println("提供者返回的响应状态码 :::>>> " + entity.getStatusCode());
        System.out.println("提供者返回的响应状态码值 :::>>> " + entity.getStatusCodeValue());
        Result result = entity.getBody();

        //为后续的负载均衡做铺垫
        result.put("consumer",8001);

        return result;
    }

    @GetMapping("/05")
    public Result consumer05(){
        //访问提供者地址
        //String url = "http://localhost:7001/provider/05/{id}/{username}?age=23&desc=我是Smith";
        String url = "http://EUREKA-CLIENT-PROVIDER/provider/05/{id}/{username}?age=23&desc=我是Smith";

        Map<String,Object> request = new HashMap<>();
        Map<String,Object> paramsMap = new HashMap<>();

        request.put("loginName","Smith");
        request.put("loginPassword","2222");

        paramsMap.put("id",1004);
        paramsMap.put("username","smith");
        /**post*/
       Result result= restTemplate.postForObject(url, request, Result.class, paramsMap);

        //为后续的负载均衡做铺垫
        result.put("consumer",8001);

        return result;
    }

    @GetMapping("/06")
    public Result consumer06(){
        //访问提供者地址
        //String url = "http://localhost:7001/provider/06/{id}/{username}?age=21&desc=我是Kitty";
        String url = "http://EUREKA-CLIENT-PROVIDER/provider/06/{id}/{username}?age=21&desc=我是Kitty";

        Map<String,Object> request = new HashMap<>();
        Map<String,Object> paramsMap = new HashMap<>();

        request.put("loginName","Kitty");
        request.put("loginPassword","3333");

        paramsMap.put("id",1005);
        paramsMap.put("username","Kitty");

        restTemplate.put(url, request, paramsMap);

        return Result.success();
    }

    @GetMapping("/07")
    public Result consumer07(){
        //访问提供者地址
//        String url = "http://localhost:7001/provider/07/{id}/{username}?age=21&desc=我是Jack";
        String url = "http://EUREKA-CLIENT-PROVIDER/provider/07/{id}/{username}?age=21&desc=我是Jack";
        Map<String,Object> paramsMap = new HashMap<>();

        paramsMap.put("id",1006);
        paramsMap.put("username","Jack");

        restTemplate.delete(url, paramsMap);

        return Result.success();
    }



}
