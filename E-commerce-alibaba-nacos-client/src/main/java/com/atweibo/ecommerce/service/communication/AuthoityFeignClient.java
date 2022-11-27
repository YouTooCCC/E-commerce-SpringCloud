package com.atweibo.ecommerce.service.communication;

import com.atweibo.ecommerce.vo.JwtToken;
import com.atweibo.ecommerce.vo.UsernameAndPassword;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

//contextId：定义的OpenFeign的id名称，如果没有定义，调用的服务只有value里面的一个。给每一个Feign Client 接口的定义
//value：要调用的服务的 application.name   名称
@FeignClient(contextId = "AuthoityFeignClient",value = "e-commerce-authoiry-center")
@Service
public interface AuthoityFeignClient {
    /**
     * 通过 OpenFeign 访问 Authority 获取 Token
     * */
    @RequestMapping(value = "/ecommerce-authoiry-center/authority/token",consumes = "application/json",produces = "application/json",method = RequestMethod.POST)
    JwtToken getTokenByFeign(@RequestBody UsernameAndPassword usernameAndPassword);


}
