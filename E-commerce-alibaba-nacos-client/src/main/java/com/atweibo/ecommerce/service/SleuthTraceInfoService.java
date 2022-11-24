package com.atweibo.ecommerce.service;


import brave.Tracer;
import com.alibaba.nacos.shaded.org.checkerframework.checker.units.qual.A;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description    本身没有任何业务含义
 *              使用代码更直观的看到sleuth生成相关跟踪信息
 * @Author weibo
 * @Data 2022/11/17 20:23
 */
@Slf4j
@Service
public class SleuthTraceInfoService {
    @Autowired
    private Tracer tracer;

    public void logCurrentTraceInfo(){
        log.info("Sleuth trace id: [{}]",tracer.currentSpan().context().traceId());
        log.info("Sleuth span id: [{}]",tracer.currentSpan().context().spanId());


    }


}
