package com.atweibo.ecommerce.service.communication;

import com.atweibo.ecommerce.vo.JwtToken;
import com.atweibo.ecommerce.vo.UsernameAndPassword;
import com.fasterxml.jackson.databind.util.Annotations;
import feign.Feign;
import feign.Logger;
import feign.form.FormEncoder;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.support.SpringMvcContract;
import org.springframework.stereotype.Service;

import javax.sql.rowset.BaseRowSet;
import javax.swing.text.TabableView;
import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Random;

/**
 * <h2>
 *     使用Feign的原生API，而不是使用OpenFeign ---->Feign+Ribbon
 * </h2>
 */
@Slf4j
@Service
public class UserFeignAPI {
    private final DiscoveryClient discoveryClient;

    public UserFeignAPI(DiscoveryClient discoveryClient) {
        this.discoveryClient = discoveryClient;
    }

    /**
     *<h3>
     *     使用Feign原生API调用远端服务
     *     feign默认配置初始化，设置自定义配置，生成代理对象
     *</h3>
     */

    public JwtToken thinkingInFeign(UsernameAndPassword usernameAndPassword){
        //通过反射去拿到ServiceId
        String serviceId = null;
        Annotation[] annotations = AuthoityFeignClient.class.getAnnotations();
        for (Annotation annotation: annotations){
            if (annotation.annotationType().equals(FeignClient.class)){
                serviceId = ((FeignClient)annotation).value();
                log.info("get service id from AuthorityFeignClient:{}",serviceId);
                break;
            }
        }

        if (serviceId == null){
            throw new RuntimeException("can not get serviceId");
        }

        //通过serviceId去哪可用服务实例
        List<ServiceInstance> trangetInstandce = discoveryClient.getInstances(serviceId);
        if (CollectionUtils.isEmpty(trangetInstandce)){
            throw new RuntimeException("can not get target instance from serviceId:"+serviceId);

        }

        //随机选择一个服务实例
        ServiceInstance serviceInstance = trangetInstandce.get(new Random().nextInt(trangetInstandce.size()));
        log.info("choose service instance:{},{},{}",serviceId,serviceInstance.getHost(),serviceInstance.getPort());

        //feign 客户端初始化，必须配置encoder，decoder、contract
        AuthoityFeignClient feignClient = Feign.builder()      //1.Feign 默认配置初始化
                .encoder(new GsonEncoder())                    //2.1 设置定义配置
                .decoder(new GsonDecoder())                    //2.2 设置定义配置
                .logLevel(Logger.Level.FULL)                   //2.3 设置定义配置
                .contract(new SpringMvcContract())
                .target(AuthoityFeignClient.class,
                        String.format("http://%s:%s",
                                serviceInstance.getHost(),serviceInstance.getPort()));
        return feignClient.getTokenByFeign(usernameAndPassword);
    }
}
