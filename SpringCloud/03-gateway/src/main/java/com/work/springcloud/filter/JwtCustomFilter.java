package com.work.springcloud.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.work.springcloud.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
 * ClassName:JwtCustomFilter
 * Package:com.work.springcloud.filter
 * Description: 描述信息
 *
 * @date:2023/5/12 18:33
 * @author:yueyue
 */
@Slf4j
@Component
public class JwtCustomFilter implements GlobalFilter, Ordered {

    @Autowired
    JwtUtils jwtUtils;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        //可以通过参数或请求头进行传递token(jwt)数据
        String token = exchange.getRequest().getQueryParams().getFirst("token");

        String uri = exchange.getRequest().getURI().toString();

        //登录操作，即申请jwt令牌操作
        if(uri.contains("/user/login")){
            //申请token，放行
            return chain.filter(exchange);
        }

        //没有通过参数进行传递，获取请求头中的token数据
        if(token == null || token.length() == 0){
            token = exchange.getRequest().getHeaders().getFirst("token");
            //没有传递token
            if(token == null || token.length() == 0){
                return errorInfo(exchange,1,"token解析失败...");
            }
            else{
                log.info("Jwt的密文数据：{}",token);
                //解析token数据
                String data = JwtUtils.vaildToken(token);
                log.info("Jwt的明文数据：{}",data);
            }
        }
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 0;
    }

    public Mono<Void> errorInfo(ServerWebExchange exchange, Integer code, String message) {

        //封装返回值结果集
        Map<String, Object> errorMap = new HashMap<>();
        errorMap.put("code", code);
        errorMap.put("message", message);
        errorMap.put("data", null);


        return Mono.defer(() -> {
            byte[] bytes = null;
            try {
                bytes = new ObjectMapper().writeValueAsBytes(errorMap);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }

            ServerHttpResponse response = exchange.getResponse();
            response.getHeaders().add("Content-Type", MediaType.APPLICATION_PROBLEM_JSON_UTF8.toString());
            DataBuffer wrap = response.bufferFactory().wrap(bytes);
            return response.writeWith(Flux.just(wrap));
        });

    }
    
}
