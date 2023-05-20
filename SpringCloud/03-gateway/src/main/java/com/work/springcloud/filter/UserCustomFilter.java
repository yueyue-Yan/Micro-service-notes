package com.work.springcloud.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

/**
 * ClassName:UserCustomFilter
 * Package:com.work.springcloud.filter
 * Description: 描述信息
 *
 * @date:2023/5/12 15:51
 * @author:yueyue
 */
@Slf4j
//@Component   //使用JwtCustomFilter过滤器了，所以把本过滤器从spring容器中拿掉
public class UserCustomFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        //在转发时，先校验Session中是否有用户对象
//        exchange.getSession().filter(webSession -> {
//            User user = webSession.getAttribute("user");
//
//            if(user == null){
//                return errorInfo(exchange,1,"没有登录，请重新登录");
//            }
//        });

        String uri = exchange.getRequest().getURI().toString();
        log.info("uri : {}",uri);

        if(uri.contains("/user/login")){
            log.info("登录操作...");
            //放行
            return chain.filter(exchange);
        }else{
            //正常进行请求转发操作
            log.info("正常进行后续请求转发操作");
        }

        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 0;
    }




    public Mono<Void> errorInfo(ServerWebExchange exchange, Integer code, String message) {

        //封装返回值结果集(map)
        Map<String, Object> errorMap = new HashMap<>();
        errorMap.put("code", code);
        errorMap.put("message", message);
        errorMap.put("data", null);

        //可读性较差，不需要深入理解
        return Mono.defer(() -> {
            //map转byte数组
            byte[] bytes = null;
            try {
                bytes = new ObjectMapper().writeValueAsBytes(errorMap);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            //获取response对象
            ServerHttpResponse response = exchange.getResponse();
            //设置当前响应头
            response.getHeaders().add("Content-Type", MediaType.APPLICATION_PROBLEM_JSON_UTF8.toString());
            //byte数组转换为DataBuffer
            DataBuffer wrap = response.bufferFactory().wrap(bytes);
            //返回最终结果
            return response.writeWith(Flux.just(wrap));
        });




    }
}
