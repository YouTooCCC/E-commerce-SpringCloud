package com.atweibo.ecommerce.controller;

import com.atweibo.ecommerce.service.NacosClientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Description
 * @Author weibo
 * @Data 2022/11/9 20:13
 */
@RestController
@Slf4j
@RequestMapping("/nacos")
public class NacosClientController {
    @Autowired
    private NacosClientService nacosClientService;
    @GetMapping("/service-instancd")
    public List<ServiceInstance> logNacosClientInfo(@RequestParam(defaultValue = "e-commerce-nacos-client")String servicdId ){
        log.info("coming in log nacos client info :[{}]",servicdId);
        return nacosClientService.getNacosClientInfo(servicdId);
    }
}
