package com.atweibo.ecommerce.filter;

import com.atweibo.ecommerce.constant.CommonConstant;
import com.atweibo.ecommerce.util.TokenParseUtil;
import com.atweibo.ecommerce.vo.LoginUserInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Description 用户身份统一登录拦截
 * @Author weibo
 * @Data 2022/11/19 10:40
 */
@Slf4j
@Component
public class LoginUserInfoInterceptor implements HandlerInterceptor {


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        /*部分请求不需要带有身份信息*/
        if (checkWhiteListUrl(request.getRequestURI())){
            return true;
        }
        /*否则执行下面的部分*/
        /*首先尝试从http中拿到 Token*/
        String token = request.getHeader(CommonConstant.JWT_USER_INFO_KEY);

        LoginUserInfo loginUserInfo = null;

        try {
            loginUserInfo = TokenParseUtil.parseUserInfoFromToken(token);

        }catch (Exception ex){
            log.error("parse login user info error:【{}】",ex.getMessage(),ex);
        }

        //如果程序走到这里，说明hearder中没有token信息,几乎不可能走到这里
        if (null == loginUserInfo){
            throw new RuntimeException("can not parse current login user");
        }

        log.info(" set login user info:【{}】",request.getRequestURI());

        /*设置当前请求上下文，把用户信息填进去*/
        AccessContext.setLoginUserInfo(loginUserInfo);


        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }
/**
 * @return void
 * @Describe:  在请求完全结束后，常用于清理资源等操作
 * @author Weibo
 * @date 2022/11/19 10:50
 */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

        if (AccessContext.getLoginUserInfo() !=null){
            AccessContext.clearLoginUserInfo();
        }

    }

    /**
     * @return boolean
     * @Describe:     校验是否是白名单接口 是否是 wagger2接口
     * @author Weibo
     * @date 2022/11/19 13:45
     */
    private boolean checkWhiteListUrl(String url){
        return StringUtils.containsAny(url,
                "springfox","swagger"
                                    ,"v2","webjars","doc.html");
    }
}
