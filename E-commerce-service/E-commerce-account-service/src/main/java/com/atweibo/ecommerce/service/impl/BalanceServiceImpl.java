package com.atweibo.ecommerce.service.impl;

import com.atweibo.ecommerce.account.BalanceInfo;
import com.atweibo.ecommerce.dao.EcommerceBalanceDao;
import com.atweibo.ecommerce.entity.EcommerceBalance;
import com.atweibo.ecommerce.filter.AccessContext;
import com.atweibo.ecommerce.service.IBalanceService;
import com.atweibo.ecommerce.vo.LoginUserInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Description 对于余额的业务实现
 * @Author weibo
 * @Data 2022/11/20 10:50
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class BalanceServiceImpl implements IBalanceService {

    @Autowired
    private EcommerceBalanceDao balanceDao;


    /*查询当前登录用户的余额信息*/
    @Override
    public BalanceInfo getCurrentUserBalanceInfo() {
        /*获取用户的登录信息*/
        LoginUserInfo loginUserInfo = AccessContext.getLoginUserInfo();

        /*new一个用户余额信息对象，通过登陆的用户信息查询到响应的对象，设置余额为0*/
        BalanceInfo balanceInfo = new BalanceInfo(loginUserInfo.getId(),0L);
        /*通过Dao接口查询用户的余额信息*/
        EcommerceBalance ecommerceBalance = balanceDao.findByUserId(loginUserInfo.getId());
        /*判断如果查询到的余额不为空，把 返回对象 余额设为查询到的余额*/
        if (ecommerceBalance != null ){
            balanceInfo.setBalance(ecommerceBalance.getBalance());
        }else {
            /*查询记录为空，创建用户余额记录，余额设定为 0 */
            EcommerceBalance newBalance = new EcommerceBalance();
            newBalance.setUserId(loginUserInfo.getId());
            newBalance.setBalance(0L);
            log.info("init user balance record:【{}】",balanceDao.save(newBalance).getId());
        }
        return balanceInfo;
    }
    /*扣减用户余额
    * 参数说明：balanceInfo是想要扣减的数额
    * */
    @Override
    public BalanceInfo deductBalance(BalanceInfo balanceInfo) {
        LoginUserInfo loginUserInfo = AccessContext.getLoginUserInfo();
        /*扣减用户的余额原则：扣减额<=余额*/
        /*获取表中的用户余额记录*/
        EcommerceBalance ecommerceBalance = balanceDao.findByUserId(loginUserInfo.getId());
        /*如果用户余额为 空  或者 查询到的用户余额 减去 传递进来扣减的余额 小于0 ，就返回异常*/
        if (ecommerceBalance == null || ecommerceBalance.getBalance() - balanceInfo.getBalance()<0){
            throw new RuntimeException("余额不足，请充值！！！");
        }
        /*用户原来的余额*/
        Long sourceBalance = ecommerceBalance.getBalance();
        /*原来的余额减去要扣减的*/
        ecommerceBalance.setBalance(ecommerceBalance.getBalance() - balanceInfo.getBalance());
        log.info("deduct balance : 【{}】,【{}】,【{}】",balanceDao.save(ecommerceBalance).getId()
                ,sourceBalance,balanceInfo.getBalance());
        /*返回user_id,余额信息*/
        return new BalanceInfo(ecommerceBalance.getUserId(),ecommerceBalance.getBalance());
    }
}
