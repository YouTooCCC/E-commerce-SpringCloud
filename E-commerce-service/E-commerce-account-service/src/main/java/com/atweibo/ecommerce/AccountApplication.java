package com.atweibo.ecommerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @Description 用户账户微服务启动入口
 * 127.0.0.1:8003/ecommerce-account-service/doc.html
 * @Author weibo
 * @Data 2022/11/19 14:39
 */
@SpringBootApplication
@EnableJpaAuditing
@EnableDiscoveryClient
@EnableSwagger2
public class AccountApplication {
    public static void main(String[] args) {
        SpringApplication.run(AccountApplication.class, args);
    }
}
