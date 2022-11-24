package com.atweibo.ecommerce.service;

import com.atweibo.ecommerce.account.AddressInfo;
import com.atweibo.ecommerce.common.TableId;

public interface IAddressService {
    /*创建当前用户登录的地址信息*/
    TableId cteateAddressInfo(AddressInfo addressInfo);

    /*获取当前登录的用户地址信息*/
    AddressInfo getCurentAddressInfo();

    /*通过id获取当前用户地址信息，id是EcommerceAddress表的主键*/
    AddressInfo getAddressInfoById(Long id);

    /*通过tableId获取用户地址信息*/
    AddressInfo getAddressInfoByTableId(TableId tableId);
}
