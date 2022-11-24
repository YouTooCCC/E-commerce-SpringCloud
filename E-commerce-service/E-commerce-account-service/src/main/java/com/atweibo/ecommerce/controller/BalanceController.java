package com.atweibo.ecommerce.controller;

import com.atweibo.ecommerce.account.BalanceInfo;
import com.atweibo.ecommerce.service.IBalanceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Description
 * @Author weibo
 * @Data 2022/11/20 15:47
 */
@Api(tags = "用户余额服务")
@RestController
@RequestMapping("/balance")
public class BalanceController {
    @Autowired
    private IBalanceService balanceService;

    @ApiOperation(value = "当前用户",notes = "获取当前用户信息",httpMethod = "GET")
    @GetMapping("/urrentBalanceInfo")
    public BalanceInfo getCurrentUserBalanceInfo(){
        return balanceService.getCurrentUserBalanceInfo();
    }

    @ApiOperation(value = "扣减",notes = "扣减余额",httpMethod = "PUT")
    @PutMapping("/deductBalance")
    public BalanceInfo deductBalance(@RequestBody BalanceInfo balanceInfo){
        return balanceService.deductBalance(balanceInfo);
    }



}
