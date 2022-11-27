package com.atweibo.ecommerce.service.communication;

import feign.Logger;
import feign.Request;
import feign.Retryer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.concurrent.TimeUnit;
import static java.util.concurrent.TimeUnit.SECONDS;


/**
 * @Description
 * @Author weibo
 * @Data 2022/11/27 16:28
 */
@Configuration
public class FeignConfig {
    /**开启Feign日志*/
    @Bean
    public Logger.Level feignLogger(){
        return Logger.Level.FULL; //需要注意，日志级别需要修改成debug
    }

    
   /**
    *<h3> 开启重试</h3>
    * period=100 发起当前请求的时间间隔，单位是ms
    * Maxperiod = 1000， 发起当前请求的最大时间间隔，单位是ms
    * maxAttempts = 5 最多请求次数
    */

    @Bean
    public Retryer feignRetryer(){
        return  new Retryer.Default(100,SECONDS.toMillis(1), 5);
    }

    public static final int CONNEXT_TIMETOUT_MILLS = 5000;
    public static final  int  READ_TIMEOUT_MILLS = 5000;

    /**
     *<h3> 对请求的连接和响应时间进行限制</h3>
     */
    @Bean
    public Request.Options options(){
        return new Request.Options(
                CONNEXT_TIMETOUT_MILLS, TimeUnit.MICROSECONDS,
                READ_TIMEOUT_MILLS,TimeUnit.MILLISECONDS,
                true
        );
    }
}
