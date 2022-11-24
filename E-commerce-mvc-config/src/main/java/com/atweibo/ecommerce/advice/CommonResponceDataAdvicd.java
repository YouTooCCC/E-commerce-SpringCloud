package com.atweibo.ecommerce.advice;

import com.atweibo.ecommerce.annotation.IgnoreResponseAdvice;

import com.atweibo.ecommerce.vo.CommonResponse;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;



/**
 * @Description        实现统一响应
 * @Author weibo
 * @Data 2022/11/9 15:26
 */
@RestControllerAdvice(value = "com.atweibo.ecommerce")/*对ResponseBody进行拦截，只能在这个包下面限制*/
/*实现了一个ResponseBodyAdvice注解，能对所有的Response对象进行拦截*/
public class CommonResponceDataAdvicd implements ResponseBodyAdvice<Object> {

    /**
     * @Describe: 是否对响应进行处理
     * @author Weibo
     * @date 2022/11/9 15:28
     */

    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        /*如果我们自定义的注解标识了一个类，那么这个类就不需要要进行特殊的处理*/
        if (methodParameter.getDeclaringClass().isAnnotationPresent(IgnoreResponseAdvice.class)){
            return false;
        }
        /*如果我们自定义的注解标识了一个方法，那么这个方法就不需要要进行特殊的处理*/
        if (methodParameter.getMethod().isAnnotationPresent(IgnoreResponseAdvice.class)){
            return false;
        }
        return true;
    }

    /**
     *
     * @Describe:        BodyWrite就是把body响应给客户端，beforeBodyWrite
     * @author Weibo
     * @date 2022/11/9 15:39
     */

    @Override
    @SuppressWarnings("all")
    public Object beforeBodyWrite(Object o, MethodParameter methodParameter, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        /*定义最终的返回对象*/
        CommonResponse<Object> response = new CommonResponse<>(0,"");
        if (null == o){
            return response;
        }else if (o instanceof CommonResponse){
            response = (CommonResponse<Object>) o;
        }else {
            /*把o变成CommonResponse类型的*/
            response.setData(o);
        }

        return response;
    }
}
