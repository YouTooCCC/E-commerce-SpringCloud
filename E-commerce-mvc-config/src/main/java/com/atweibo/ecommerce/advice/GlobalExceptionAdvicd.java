package com.atweibo.ecommerce.advice;

import com.atweibo.ecommerce.vo.CommonResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description     全局异常捕获处理。
 * service和controller在执行的过程中可能会出现异常情况，
 *  不想让客户端直接抛出异常，还是处理统一响应。
 * @Author weibo
 * @Data 2022/11/9 15:54
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionAdvicd {

    /*这个注解会对系统中的所有异常进行拦截，就不会对客户端返回，而是通过下面的方法进行处理*/
    @ExceptionHandler(value = Exception.class)
    public CommonResponse<String> handlerCommerceException(HttpServletRequest req,Exception ex){
        CommonResponse<String> response = new CommonResponse<>(-1,"Application error");
        response.setData(ex.getMessage());
        log.error("Commerce service has error:[{}]",ex.getMessage(),ex);

        return response;
    }
}
