package com.atweibo.ecommerce.controller;

import com.alibaba.fastjson.JSON;
import com.atweibo.ecommerce.annotation.IgnoreResponseAdvice;
import com.atweibo.ecommerce.service.IJWTService;
import com.atweibo.ecommerce.vo.JwtToken;
import com.atweibo.ecommerce.vo.UsernameAndPassword;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description 对外暴露的授权服务接口
 * @Author weibo
 * @Data 2022/11/11 13:44
 */
@Slf4j
@RestController
@RequestMapping("/authority")
public class AuthorityController {

    private final IJWTService iJwtService;

    public AuthorityController(IJWTService iJwtService) {
        this.iJwtService = iJwtService;
    }

    /**
     * @return com.atweibo.ecommerce.vo.JwtToken
     * @Describe:       从授权中心获取Token（其实就是登录功能）
     *                  而且返回信息中没有统一响应的包装
     * @author Weibo
     * @date 2022/11/11 13:47
     */
    @IgnoreResponseAdvice
    @PostMapping("/token")
    public JwtToken token(@RequestBody UsernameAndPassword usernameAndPassword) throws Exception{

        log.info("request to get token with param:[{}]", JSON.toJSONString(usernameAndPassword));

        return new JwtToken(iJwtService.generateToken(
                usernameAndPassword.getUsername(),
                usernameAndPassword.getPassword()));
    }


    /**
     * @return com.atweibo.ecommerce.vo.JwtToken
     * @Describe:       注册用户并返回当前注册用户对象的token， 即通过授权中心创建用户
     * @author Weibo
     * @date 2022/11/21 14:45
     */

    @IgnoreResponseAdvice
    @PostMapping("/register")
        public JwtToken register(@RequestBody UsernameAndPassword usernameAndPassword)throws Exception{
        log.info("regist user with param:[{}]",JSON.toJSONString(usernameAndPassword));
        return  new JwtToken(iJwtService.registerUserAndGenerateToken(usernameAndPassword));

        }

}
