package com.atweibo.ecommerce.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @Description 网关需要注入到容器中的Bean
 * @Author weibo
 * @Data 2022/11/16 16:30
 */
@Configuration
public class GatewayBeanConf {

    @Bean
    public RestTemplate restTemplate(){
       return new RestTemplate();
    }
}
