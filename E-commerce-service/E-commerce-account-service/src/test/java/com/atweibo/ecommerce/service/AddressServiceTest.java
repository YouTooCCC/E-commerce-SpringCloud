package com.atweibo.ecommerce.service;

import com.alibaba.fastjson.JSON;
import com.atweibo.ecommerce.account.AddressInfo;
import com.atweibo.ecommerce.common.TableId;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;

/**
 * @Description  用户地址相关服务功能测试
 * @Author weibo
 * @Data 2022/11/19 20:19
 */
@Slf4j
public class AddressServiceTest extends BaseTest{
    @Autowired
    private IAddressService addressService;

    @Test
    public void testCreateAddressInfo()
    {
        AddressInfo.AddressItem addressItem = new AddressInfo.AddressItem();
        addressItem.setUsername("啵啵啵");
        addressItem.setPhone("82247559");
        addressItem.setProvince("江苏省");
        addressItem.setCity("徐州市");
        addressItem.setAddressDetail("泉山区金山东路中国矿业大学");

        log.info("test create addrssinfo:【{}】", JSON.toJSONString(
                addressService.cteateAddressInfo(
                        new AddressInfo(loginUserInfo.getId()
                                ,Collections.singletonList(addressItem)))
        ));

    }


    @Test
    public void testGetAddressCurrentAddressinfo(){
        log.info("test get current user info:【{}】",JSON.toJSONString(addressService.getCurentAddressInfo()));

    }

    @Test
    public void testGetAddressInfoById(){
        log.info("test get AddressInfo by Id:【{}】",JSON.toJSONString(addressService.getAddressInfoById(1L)));
    }

    @Test
    public void testgetAddressInfoByTableId(){
        log.info("test get AddressInfo by Id:【{}】",JSON.toJSONString(
                addressService.getAddressInfoByTableId(new TableId(Collections.singletonList(new TableId.Id(1L))))));

    }
}
