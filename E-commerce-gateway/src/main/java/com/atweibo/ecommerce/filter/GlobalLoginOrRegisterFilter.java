package com.atweibo.ecommerce.filter;

import com.alibaba.fastjson.JSON;
import com.atweibo.ecommerce.constant.CommonConstant;
import com.atweibo.ecommerce.constant.GatewayConstant;
import com.atweibo.ecommerce.util.TokenParseUtil;
import com.atweibo.ecommerce.vo.JwtToken;
import com.atweibo.ecommerce.vo.LoginUserInfo;
import com.atweibo.ecommerce.vo.UsernameAndPassword;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.*;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.CharBuffer;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @Description 全局登录鉴权过滤器
 * @Author weibo
 * @Data 2022/11/16 16:16
 */
@Component
@Slf4j
public class GlobalLoginOrRegisterFilter implements GlobalFilter, Ordered {
    private final LoadBalancerClient loadBalancerClient;
    private final RestTemplate restTemplate;

    public GlobalLoginOrRegisterFilter(LoadBalancerClient loadBalancerClient, RestTemplate restTemplate) {
        this.loadBalancerClient = loadBalancerClient;
        this.restTemplate = restTemplate;
    }

    /*
     *登陆、注册、鉴权
     * 1.如果是登陆或者注册，则去授权中心拿到Token，并返回给客户端
     * 2.如果是访问其他的服务，则鉴权，没有权限就返回401
     * */

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        /*1.如果是登陆*/
        if (request.getURI().getPath().contains(GatewayConstant.LOGIN_URI)) {
            /*去鉴权中心 拿到token*/
            String token = getTokenFromAuthorityCenter(request, GatewayConstant.AUTHORITY_CENTER_TOKEN_URL_FORMAT);
            /*header 中不能设置null*/
            response.getHeaders().add(CommonConstant.JWT_USER_INFO_KEY, token == null ? "null" : token);
            response.setStatusCode(HttpStatus.OK);
            return response.setComplete();
        }

        /*2.如果是注册*/
        if (request.getURI().getPath().contains(GatewayConstant.REGISTER_URI)) {
            /*去授权中心拿 token ：先创建用户，再返回token*/
            String token = getTokenFromAuthorityCenter(request, GatewayConstant.AUTHORITY_CENTER_REGISTER_URL_FORMAT);
            response.getHeaders().add(CommonConstant.JWT_USER_INFO_KEY, token == null ? "null" : token);
            response.setStatusCode(HttpStatus.OK);
            return response.setComplete();
        }
        /*3.访问其他服务，则鉴权，校验是否能够从Token中解析出用户信息*/
        HttpHeaders headers = request.getHeaders();
        String token = headers.getFirst(CommonConstant.JWT_USER_INFO_KEY);
        LoginUserInfo loginUserInfo = null;

        try {
            loginUserInfo = TokenParseUtil.parseUserInfoFromToken(token);
        } catch (Exception ex) {
            log.info("parse user info from token error:【{}】", ex.getMessage(), ex);
        }
        /*获取不到登录用户信息，返回401*/
        if (loginUserInfo == null) {
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return response.setComplete();
        }
        /*解析通过则放行*/
        return chain.filter(exchange);
    }



    /*从授权中心获取Token*/
    private String getTokenFromAuthorityCenter(ServerHttpRequest request, String uriFormat) {
        /*service id就是服务名字，负载均衡，通过nacos服务注册中心找到实例，选择实例*/
        ServiceInstance serviceInstance = loadBalancerClient.choose(
                CommonConstant.AUTHORITY_CENTER_SERVICE_ID
        );
        log.info("Nacos Client Info:[{}]", serviceInstance.getInstanceId(), JSON.toJSONString(serviceInstance.getMetadata()));

        /*拼接url*/
        String requestUrl = String.format(
                uriFormat, serviceInstance.getHost(), serviceInstance.getPort());

        UsernameAndPassword requestBody =
                JSON.parseObject(parseBodyFromRequest(request), UsernameAndPassword.class);
        log.info("登录请求的url和body为：[{}]",requestUrl,JSON.toJSONString(requestBody));

        /*发起http请求*/
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        JwtToken token = restTemplate.postForObject(requestUrl
                , new HttpEntity<>(JSON.toJSONString(requestBody), headers)
                , JwtToken.class);

        if (token != null) {
            return token.getToken();
        }
        return null;
    }

    /*从post请求中获取请求数据*/
    private String parseBodyFromRequest(ServerHttpRequest request) {
        /*获取请求体*/
        Flux<DataBuffer> body = request.getBody();
        /*原子引用*/
        AtomicReference<String> bodyRef = new AtomicReference<>();
        /*订阅缓冲区消费请求体中的数据*/
        body.subscribe(buffer -> {
            CharBuffer charBuffer = StandardCharsets.UTF_8.decode(buffer.asByteBuffer());
            /*一定要使用DataBufferUtils.release释放掉，否则会出现内存泄露*/
            DataBufferUtils.release(buffer);
            bodyRef.set(charBuffer.toString());
        });

        /*获取request.body*/
        return bodyRef.get();
    }



    @Override
    public int getOrder() {
        return HIGHEST_PRECEDENCE + 2;
    }
}
