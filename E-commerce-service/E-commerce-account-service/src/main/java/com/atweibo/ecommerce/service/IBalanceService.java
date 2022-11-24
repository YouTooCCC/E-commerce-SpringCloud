package com.atweibo.ecommerce.service;

import com.atweibo.ecommerce.account.BalanceInfo;

/*用于余额相关的接口*/
public interface IBalanceService {

    /*获取当前用户余额信息*/
    BalanceInfo getCurrentUserBalanceInfo();

    /*扣减用户余额
    *@param 参数代表的是：想要扣减的余额
    * */
    BalanceInfo deductBalance(BalanceInfo balanceInfo);
}
