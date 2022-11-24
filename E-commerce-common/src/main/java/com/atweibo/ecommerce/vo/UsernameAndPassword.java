package com.atweibo.ecommerce.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description     用户名和密码
 * @Author weibo
 * @Data 2022/11/10 21:08
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsernameAndPassword {
    /*用户名*/
    private String username;
    /*密码*/
    private String password;
}
