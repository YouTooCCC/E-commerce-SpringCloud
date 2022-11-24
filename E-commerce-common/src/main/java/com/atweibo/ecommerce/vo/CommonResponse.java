package com.atweibo.ecommerce.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Description     通用响应对象
 * @return          {
 *                      "code":0,
 *                       "message":"",
 *                       "data",{}
 *                  }
 * @Author weibo
 * @Data 2022/11/9 15:05
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommonResponse<T> implements Serializable {
    /*响应码，错误码*/
    private Integer code;
    /*响应消息，错误消息*/
    private String message;
    /*泛型响应数据*/
    private T Data;

    public CommonResponse(Integer code,String message){
        this.code = code;
        this.message = message;
    }

}
