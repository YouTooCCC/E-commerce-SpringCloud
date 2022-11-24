package com.atweibo.ecommerce.service;

import com.alibaba.fastjson.JSON;
import com.atweibo.ecommerce.account.BalanceInfo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @Description  用户余额功能测试
 * @Author weibo
 * @Data 2022/11/20 14:26
 */
@SpringBootTest
@Slf4j
public class BalanceServiceTest extends BaseTest {
    @Autowired
    private IBalanceService balanceService;


    @Test
    public void testGetCurrentUserBalanceInfo(){

        log.info("test current user's balance:【{}】", JSON.toJSONString(
                balanceService.getCurrentUserBalanceInfo()
        ));
    }

    @Test
    public void testDeductBalance(){
        BalanceInfo balanceInfo = new BalanceInfo();
        balanceInfo.setUserId(loginUserInfo.getId());
        balanceInfo.setBalance(1000L);

        log.info("test deduct balance: 【{}】",JSON.toJSONString(balanceService.deductBalance(balanceInfo)));
    }
}
