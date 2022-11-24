package com.atweibo.ecommerce.filter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @Description     HTTP请求头部携带 Token验证过滤器，局部过滤器
 * @Author weibo
 * @Data 2022/11/16 15:16
 */

public class HeaderTokenGatewayFilter implements GatewayFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        /*从HTTP header中寻找key为token，value为imooc的键值对*/
        String name = exchange.getRequest().getHeaders().getFirst("token");
        if ("imooc".equals(name)){
            return chain.filter(exchange);
        }

        //标记此次请求没有权限401,状态码，并结束此次请求
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        /*setComplete()  请求结束*/
        return exchange.getResponse().setComplete();
    }

    /*优先级*/
    @Override
    public int getOrder() {
        /*最高优先级+2*/
        return HIGHEST_PRECEDENCE + 2;
    }
}
