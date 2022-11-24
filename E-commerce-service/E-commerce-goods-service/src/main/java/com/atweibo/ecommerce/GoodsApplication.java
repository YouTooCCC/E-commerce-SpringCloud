package com.atweibo.ecommerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @Description     使用了Redis、mysql、Nacos、kafka、Zipkin
 *  http://localhost:8001/ecommerce-goods-service/doc.html
 *
 * @Author weibo
 * @Data 2022/11/22 10:56
 */
@SpringBootApplication
@EnableSwagger2
@EnableDiscoveryClient
@EnableJpaAuditing
@EnableAsync
public class GoodsApplication {
    public static void main(String[] args) {
        SpringApplication.run(GoodsApplication.class, args);
    }
}
