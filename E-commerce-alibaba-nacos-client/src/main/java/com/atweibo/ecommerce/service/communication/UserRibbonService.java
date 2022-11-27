package com.atweibo.ecommerce.service.communication;

import com.alibaba.fastjson.JSON;
import com.atweibo.ecommerce.constant.CommonConstant;
import com.atweibo.ecommerce.vo.JwtToken;
import com.atweibo.ecommerce.vo.UsernameAndPassword;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @Description
 * @Author weibo
 * @Data 2022/11/27 15:28
 */
@Slf4j
@Service
public class UserRibbonService {

    private final RestTemplate restTemplate;

    public UserRibbonService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    public JwtToken getTokenFromAuthorityServiceByRibbon(UsernameAndPassword usernameAndPassword){

        //注意到url中的ip和段偶转换成了服务名称
        String requestUrl = String.format("http://%s/ecommerce-authoiry-center/authority/token", CommonConstant.AUTHORITY_CENTER_SERVICE_ID);
        log.info("login request url and body:{},{}",requestUrl, JSON.toJSONString(usernameAndPassword));


        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return restTemplate.postForObject(
                requestUrl,
                new HttpEntity<>(JSON.toJSONString(usernameAndPassword),headers),
                JwtToken.class
        );
    }
}
