package com.atweibo.ecommerce.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description     授权中心给 鉴权之后 给客户端的Token
 * 定义为对象，方便以后使用。
 * @Author weibo
 * @Data 2022/11/10 21:09
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JwtToken {
    /*JWT*/
    private String token;
}
