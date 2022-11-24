package com.atweibo.ecommerce.filter;

import com.atweibo.ecommerce.vo.LoginUserInfo;

/**
 * @Description 使用ThreadLocal 去单独存储每一个线程携带的LoginUserInfo信息
 *            1.要及时清理保存的 ThreadLocal 中的用户信息
 *            2，保证资源不会泄露
 *            3.保证线程在重用时，不会出现数据混乱
 * @Author weibo
 * @Data 2022/11/19 10:29
 */

public class AccessContext {
    /*将用户信息保存在线程安全的变量里面*/
    public static final ThreadLocal<LoginUserInfo> loginUserInfo = new ThreadLocal<>();

    /**
     * @return com.atweibo.ecommerce.vo.LoginUserInfo
     * @Describe:       在微服务中使用，在请求中获取到用户信息
     * @author Weibo
     * @date 2022/11/19 10:36
     */
    public static LoginUserInfo getLoginUserInfo(){
        return loginUserInfo.get();
    }

    /**
     * @return void
     * @Describe: 从拦截器解析用户请求 JWT中拿到信息
     * @author Weibo
     * @date 2022/11/19 10:37
     */
    public static void setLoginUserInfo(LoginUserInfo loginUserInfo_){
        loginUserInfo.set(loginUserInfo_);
    }

    /**
     * @return void
     * @Describe:  清理掉用户信息，保证资源不泄露，保证线程重用的时候数据不会混乱
     * @author Weibo
     * @date 2022/11/19 10:39
     */
    public static void clearLoginUserInfo(){
        loginUserInfo.remove();
    }
}
