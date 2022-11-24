package com.atweibo.ecommerde;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Description       监控中心服务器
 * @Author weibo
 * @Data 2022/11/9 20:46
 */
@EnableAdminServer
@SpringBootApplication
public class AdmianApplication {
    public static void main(String[] args) {
        SpringApplication.run(AdmianApplication.class, args);
    }
}
