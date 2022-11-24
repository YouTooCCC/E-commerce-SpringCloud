package com.atweibo.ecommerce.service;

import com.atweibo.ecommerce.vo.UsernameAndPassword;


/**
 * @Description        JWT服务接口定义
 * @Author weibo
 * @Data 2022/11/11 9:00
 */

public interface IJWTService {
    /** 生成JWT token ，只有登录之后才能给用户一个token，使用默认超时时间*/
    String generateToken(String username,String password)throws Exception;

    /** 生成指定超时时间的 token */
    String generateToken(String username ,String password,int expire)throws Exception;

    /** 注册用户并生成token ，返回。   提供给用户*/
    String registerUserAndGenerateToken(UsernameAndPassword usernameAndPassword)throws Exception;

}
