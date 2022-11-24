package com.atweibo.ecommerce;
/*用户地址相关服务接口定义*/

import com.atweibo.ecommerce.account.AddressInfo;
import com.atweibo.ecommerce.common.TableId;

public interface IAddressService {

    /*创建用户地址信息*/
    TableId createAddressInfo(AddressInfo addressInfo);

    /*获取当前登录的用户地址信息*/
    AddressInfo getCurrentAddressInfo();

    /*通过id获取用户地址信息*/
    AddressInfo getAddressInfoById(Long id);

    /*通过TableId获取当前地址信息*/
    AddressInfo getAddressInfoByTableId(TableId tableId);
}
