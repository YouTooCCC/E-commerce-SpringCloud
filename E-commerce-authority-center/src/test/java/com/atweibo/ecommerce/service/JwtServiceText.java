package com.atweibo.ecommerce.service;

import cn.hutool.crypto.digest.MD5;
import com.alibaba.fastjson.JSON;
import com.atweibo.ecommerce.dao.EcommerceUserDao;

import com.atweibo.ecommerce.entity.EcommerceUser;
import com.atweibo.ecommerce.util.TokenParseUtil;
import com.atweibo.ecommerce.vo.JwtToken;
import com.atweibo.ecommerce.vo.LoginUserInfo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Description
 * @Author weibo
 * @Data 2022/11/11 14:37
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class JwtServiceText {
    @Autowired
    private EcommerceUserDao ecommerceUserDao;
    @Autowired
    private IJWTService ijwtService;


    @Test
    public void testGeneraamdParseToken() throws Exception {
        String jwtToken = ijwtService.generateToken("bobo", "123456");

        log.info("jwt token is:{}",jwtToken);
        LoginUserInfo loginUserInfo = TokenParseUtil.parseUserInfoFromToken(jwtToken);
        log.info("parse token: {}", JSON.toJSONString(loginUserInfo));
    }



    @Test
    public void testGenerateAndParseToken()throws Exception{

        EcommerceUser ecommerUser = new EcommerceUser();
        ecommerUser.setUsername("cccccc");
        ecommerUser.setPassword(MD5.create().digestHex("123456"));
        ecommerUser.setExtraInfo("I am cccc");
        log.info("添加了用户：[{}],[{}]",JSON.toJSONString(ecommerceUserDao.save(ecommerUser)));



    }
}
