package com.atweibo.ecommerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @Description
 * @Author weibo
 * @Data 2022/11/11 18:52
 */
@SpringBootApplication
@EnableDiscoveryClient
public class GatewatApplication {
    public static void main(String[] args) {
        SpringApplication.run(GatewatApplication.class, args);
    }
}
