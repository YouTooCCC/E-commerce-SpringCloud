package com.atweibo.ecommerce.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description 已经登录的用户信息，通过解析JWTToken来获得LoginUserInfo
 * @Author weibo
 * @Data 2022/11/10 21:10
 */


@Data
@NoArgsConstructor
@AllArgsConstructor

public class LoginUserInfo {
    /*用户id*/
    private long id;

    /*用户名*/
    private String username;
}
