package com.atweibo.ecommerce.filter.factory;

import com.atweibo.ecommerce.filter.HeaderTokenGatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;

/**
 * @Description  将局部过滤器加入到工厂之中，并加入到spring中，还需要到配置文件中配置。用于存储局部过滤器
 * @Author weibo
 * @Data 2022/11/16 15:40
 */
@Component
public class HeaderTokenGatewayFilterFactory extends AbstractGatewayFilterFactory<Object> {
    @Override
    public GatewayFilter apply(Object config) {
        return new HeaderTokenGatewayFilter();
    }
}
