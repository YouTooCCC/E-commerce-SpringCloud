package com.atweibo.ecommerce.constant;

/**
 * @Description
 * @Author weibo
 * @Data 2022/11/16 15:48
 */

public class GatewayConstant {
    /*登录*/
    public static final String LOGIN_URI = "/e-commerce/login";
    /*注册*/
    public static final String REGISTER_URI = "/e-commerce/register";

    /*去授权中心拿到登录token的uri格式化接口，拿token*/
    public static final String AUTHORITY_CENTER_TOKEN_URL_FORMAT =
            "http://%s:%s/ecommerce-authoiry-center/authority/token";

    /*去授权中心 注册 并拿到token的uri格式化接口*/
    public static final String AUTHORITY_CENTER_REGISTER_URL_FORMAT =
            "http://%s:%s/ecommerce-authoiry-center/authority/register";

}
