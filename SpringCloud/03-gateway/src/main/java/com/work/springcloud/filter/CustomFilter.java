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
 * ClassName:CustomFilter
 * Package:com.work.springcloud.filter
 * Description: 自定义过滤器
 *
 * @date:2023/5/12 11:52
 * @author:yueyue
 */
@Slf4j
//@Component  //使用UserCustomFilter过滤器了，所以把本过滤器从spring容器中拿掉
public class CustomFilter implements GlobalFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info("过滤器执行了...");

        //最简单的权限拦截操作
        String token = exchange.getRequest().getQueryParams().getFirst("token");
        if(token == null || token.length() == 0){
            log.info("Token校验失败");
            return errorInfo(exchange,1,"Token校验失败");
        }
        else {
            log.info("用户携带了身份令牌,需要进一步验证这个身份是否真的合法");
        }
        //放行操作，访问后续的过滤器或访问请求转发到网关操作
        return chain.filter(exchange);
    }
    /**
     * 过滤器的优先级
     *      数字越小，优先级越高
     *          int HIGHEST_PRECEDENCE = -2147483648;
     *          int LOWEST_PRECEDENCE  = 2147483647;
     */
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
