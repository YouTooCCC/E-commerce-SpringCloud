package com.atweibo.ecommerce.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description
 * @Author weibo
 * @Data 2022/11/9 20:08
 */
@Slf4j
@Service
public class NacosClientService {

    private final DiscoveryClient discoveryClient;
    public NacosClientService(DiscoveryClient discoveryClient) {
        this.discoveryClient = discoveryClient;
    }
    /*
    * 打印nacos client信息到日志中
    * */
    public List<ServiceInstance> getNacosClientInfo(String servideId){
        log.info("nacos client to get service instancd inf:[{}]",servideId);
        return discoveryClient.getInstances(servideId);
    }
}
