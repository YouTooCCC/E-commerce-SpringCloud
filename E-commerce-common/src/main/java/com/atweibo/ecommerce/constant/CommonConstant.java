package com.atweibo.ecommerce.constant;



/**
 * @Description 通用模块定义
 * @Author weibo
 * @Data 2022/11/10 21:00
 */

public class CommonConstant {
    /*公钥*/
    public static final String PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAnJvyYvdVbpiOXZR1/B19G" +
            "hagcOqLsBzjcMhQ15bQzOCHvOycaqVOCD0F2fsmURgC11+yN3dwqHvPaaCjOp1TtuuLD58" +
            "l7r7cUqt7c9rpAlenTc4Q9fp5fUryZ4cBghNzOOA0EbXX/yXfuHqDHqwzGmUSn0So37Njf" +
            "9HPLtuGdMPZBX5FILABvgubRIhqoQ9hBYjl5tuYgKFOVdG9rh6Erydz7ryPIsaHAJ9mxCs" +
            "S+N6wPr577YYVubd2mEUVwX4unK3MdPMt01XRR/b7cDpN9eLuSMg+bYlsRh4M5FmmJeWdFF" +
            "ZEpcHkxApb5ir48jMLhJlFkee4MmBL2ek5kKJ3QQIDAQAB";

    /*JWT中存储用户信息的key，使用到token中*/
    public static final String JWT_USER_INFO_KEY = "E-commerce-user";

    /*授权中心的service-id，会通过nacos来查找对应的授权中心，实现功能的调用*/
    public static final String AUTHORITY_CENTER_SERVICE_ID = "e-commerce-authoiry-center";
}
