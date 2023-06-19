package com.iaven.Filter;


import com.iaven.service.JwtApiservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.concurrent.*;

@Component
public class TokenFilter implements GlobalFilter, Ordered {

    @Autowired
    JwtApiservice jwtApiservice;

    ExecutorService executorService = Executors.newFixedThreadPool(1);

    @Override
    public int getOrder() {
        return 0;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        //2.判断当前的请求是否为登录，如果是，直接放行
        if(request.getURI().getPath().contains("/login")||request.getURI().getPath().contains("/mail")||request.getURI().getPath().contains("/test")){
            //放行
            System.out.println("登录放行");
            return chain.filter(exchange);
        }
        HttpHeaders headers = request.getHeaders();
        String jwtToken = headers.getFirst("token");
        System.out.println(jwtToken);
        //4.判断当前令牌是否存在
        if(jwtToken==null){
            //如果不存在，向客户端返回错误提示信息
            System.out.println("缺少验证信息");
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return response.setComplete();
        }
        //5.如果令牌存在，解析jwt令牌，判断该令牌是否合法，如果不合法，则向客户端返回错误信息
        Future<Boolean> future = executorService.submit(() ->jwtApiservice.check(jwtToken));
        boolean permitted = false;
        try {
            permitted = future.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        if(!permitted){
            System.out.println("验证信息错误");
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return response.setComplete();
        }
        //6.放行
        return chain.filter(exchange);
    }
}
