package com.atweibo.ecommerce.service.communication;
/*使用restTemplate实现微服务通信*/

import com.alibaba.fastjson.JSON;
import com.atweibo.ecommerce.constant.CommonConstant;
import com.atweibo.ecommerce.vo.JwtToken;
import com.atweibo.ecommerce.vo.UsernameAndPassword;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class UserRestTemplateService {
    //注入   实现服务在均衡的客户端
    private final LoadBalancerClient loadBalancerClient;
    public UserRestTemplateService(LoadBalancerClient loadBalancerClient) {
        this.loadBalancerClient = loadBalancerClient;
    }

    public JwtToken getTokenFromAuthorityService(UsernameAndPassword usernameAndPassword){

        /**
         * 从授权服务中获取JwtToken
         * */
        String requestUrl = "http://127.0.0.1:7000/ecommerce-authoiry-center/authority/token";
        log.info("RestTemplate request url and body:{},{}",requestUrl, JSON.toJSONString(usernameAndPassword));
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return  new RestTemplate().postForObject(requestUrl,
                                                 new HttpEntity<>(JSON.toJSONString(usernameAndPassword),httpHeaders),
                                                 JwtToken.class);
    }

    public JwtToken getTokenFromAuthorityServiceWithLoadBalancer(UsernameAndPassword usernameAndPassword){
        //第二种通信方式，通过注册中心拿到服务的信息（所有的实例），再去发起调用
        ServiceInstance serviceInstance = loadBalancerClient.choose(CommonConstant.AUTHORITY_CENTER_SERVICE_ID);
        log.info("Nacos Client Info:{},{},{}",serviceInstance,serviceInstance.getInstanceId(),serviceInstance.getServiceId());
        String requestUrl =String.format("http://%s:%s/ecommerce-authoiry-center/authority/token", serviceInstance.getHost(),serviceInstance.getPort());

        log.info("login request url and body:{},{}",requestUrl,JSON.toJSONString(usernameAndPassword));

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return  new RestTemplate().postForObject(requestUrl,
                new HttpEntity<>(JSON.toJSONString(usernameAndPassword),httpHeaders),
                JwtToken.class);
    }

}
