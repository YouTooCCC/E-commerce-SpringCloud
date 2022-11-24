package com.atweibo.ecommerce.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Description    由于我们使用common响应，但是不是所有的都需要返回common
 *                  自定义注解
 * @Author weibo
 * @Data 2022/11/9 15:15
 */

@Target({ElementType.TYPE,ElementType.METHOD})/*可以标注在controller上，controller 的方法上*/
@Retention(RetentionPolicy.RUNTIME)/*还要在运行时生效*/
public @interface IgnoreResponseAdvice {


}
