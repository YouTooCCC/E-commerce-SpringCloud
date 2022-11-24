package com.atweibo.ecommerce.filter;

import com.atweibo.ecommerce.constant.GatewayConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @Description     缓存 HTTP 请求 body 的全局过滤器   spring WebFlux
 * 通过网关访问鉴权微服务时，通过鉴权微服务可以完成注册和登录。
 *
 * @Author weibo
 * @Data 2022/11/16 15:45
 */
@Slf4j
@Component
public class GlobalCacheRequestBodyFilter implements GlobalFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        /*判断是否是登录或者是注册的URI*/
        boolean isloginOrRegister =
                exchange.getRequest().getURI().getPath().contains(GatewayConstant.LOGIN_URI)
                ||exchange.getRequest().getURI().getPath().contains(GatewayConstant.REGISTER_URI);

        if (null == exchange.getRequest().getHeaders().getContentType() ||!isloginOrRegister){
            return chain.filter(exchange);
        }
        /*DataBufferUtils.join 拿到请求中的数据-->DataBuffer*/
        return DataBufferUtils.join(exchange.getRequest().getBody()).flatMap(dataBuffer -> {
            /*确保数据缓冲区的不被释放，必须要使用DataBufferUtils.retain()*/
            DataBufferUtils.retain(dataBuffer);
            /*defer、just都是去创建数据源，得到当前数据的副本*/
            Flux<DataBuffer> cachedFlux = Flux.defer(()->
                    Flux.just(dataBuffer.slice(0,dataBuffer.readableByteCount())));
            /*重新包装ServerHttpRequest，重写getbody方法，能够返回请求数据*/
            ServerHttpRequest mutatedRequest = new ServerHttpRequestDecorator(exchange.getRequest()){
                @Override
                public Flux<DataBuffer> getBody() {
                    return cachedFlux;
                }

            };
            /*将包装过后的ServerHttpRequest向下继续传递*/
            return chain.filter(exchange.mutate().request(mutatedRequest).build());
        });
    }

    @Override
    public int getOrder() {
        return HIGHEST_PRECEDENCE + 1;
    }
}
