package com.atweibo.ecommerce.controller;

import com.atweibo.ecommerce.service.communication.AuthoityFeignClient;
import com.atweibo.ecommerce.service.communication.UserFeignAPI;
import com.atweibo.ecommerce.service.communication.UserRestTemplateService;
import com.atweibo.ecommerce.service.communication.UserRibbonService;
import com.atweibo.ecommerce.vo.JwtToken;
import com.atweibo.ecommerce.vo.UsernameAndPassword;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description
 * @Author weibo
 * @Data 2022/11/27 15:02
 */
@RestController
@RequestMapping("/communication")
public class CommunicationController {
    private final AuthoityFeignClient authoityFeignClient;
    private final UserFeignAPI userFeignAPI;
    private final UserRibbonService ribbonService;

    private final UserRestTemplateService restTemplateService;

    public CommunicationController(UserRestTemplateService restTemplateService, UserRibbonService ribbonService, AuthoityFeignClient authoityFeignClient, UserFeignAPI userFeignAPI) {
        this.restTemplateService = restTemplateService;
        this.ribbonService = ribbonService;
        this.authoityFeignClient = authoityFeignClient;
        this.userFeignAPI = userFeignAPI;
    }

    @PostMapping("/restTemplate")
     public JwtToken getTokenFromAuthorityService(@RequestBody UsernameAndPassword usernameAndPassword){
        return restTemplateService.getTokenFromAuthorityService(usernameAndPassword);
     }
    @PostMapping("/loadBalancer")
     public JwtToken getTokenFromAuthorityServiceWithLoadBalancer(@RequestBody UsernameAndPassword usernameAndPassword){
        return restTemplateService.getTokenFromAuthorityServiceWithLoadBalancer(usernameAndPassword);

     }


    @PostMapping("/ribbon")
     public JwtToken getTokenFromAuthorityServiceByRibbon(@RequestBody UsernameAndPassword usernameAndPassword){
        return ribbonService.getTokenFromAuthorityServiceByRibbon(usernameAndPassword);
     }

    @PostMapping("/openFeign")
    public JwtToken getTokenByFeign(@RequestBody UsernameAndPassword usernameAndPassword){
        return authoityFeignClient.getTokenByFeign(usernameAndPassword);
    }

    @PostMapping("/inFeign")
    public JwtToken thinkingInFeign(@RequestBody UsernameAndPassword usernameAndPassword){
        return userFeignAPI.thinkingInFeign(usernameAndPassword);
    }



}
