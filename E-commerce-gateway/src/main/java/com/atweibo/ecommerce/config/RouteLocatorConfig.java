package com.atweibo.ecommerce.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description 配置登录请求转发规则
 * @Author weibo
 * @Data 2022/11/16 19:16
 */
@Configuration
public class RouteLocatorConfig {
    /*简单的路由规则          复杂的还是建议使用yaml配置
    * 使用代码定义路由规则，在网关层面蓝家下登录和注册接口
    * */


    /*手动指定Gateway路由规则需要指定id、path、uri*/
    @Bean
    public RouteLocator loginRouteLocator(RouteLocatorBuilder builder){
        return builder.routes()
                .route(
                        "e_commerce_authority",
                        r->r.path("/imooc/e-commerce/login",
                                           "/imooc/e-commerce/register")
                                .uri("http://localhost:9001/"))
                                            .build();

    }
}
