package com.atweibo.ecommerce.service.communication;

import feign.Feign;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * <h2>
 *     okhttp 的配置类
 * </h2>
 */
@Configuration
@ConditionalOnClass(Feign.class)//Feign存在的时候，当前这个类才生效
@AutoConfigureBefore(FeignAutoConfiguration.class)//在FeignAutoConfiguration之前实现 FeignOkhttp才会生效
public class FeignOkhttp {
    @Bean
    public okhttp3.OkHttpClient okHttpClient(){
        return new  OkHttpClient.Builder()
                .connectTimeout(5, TimeUnit.SECONDS) //设置连接超时
                .readTimeout(5, TimeUnit.SECONDS)   //设置读超时
                .writeTimeout(5,TimeUnit.SECONDS) //设置写超时
                .retryOnConnectionFailure(true) //是否自动连接
                // 配置连接池中的最大空闲线程个数为10，保持5分钟
                .connectionPool(new ConnectionPool(
                        10,5L,TimeUnit.MINUTES
                )).build();
    }
}
