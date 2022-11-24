package com.atweibo.ecommerce.controller;

import com.atweibo.ecommerce.service.SleuthTraceInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description
 * @Author weibo
 * @Data 2022/11/17 20:29
 */
@RestController
@Slf4j
@RequestMapping("/sleuth")
public class SteuthTraceInfoController {
    @Autowired
    private SleuthTraceInfoService traceInfoService;

    
    @GetMapping("/trace-info")
    public void logCurrentTraceInfo(){
        traceInfoService.logCurrentTraceInfo();
    }
}
