package com.atweibo.ecommerce.controller;

import com.atweibo.ecommerce.account.AddressInfo;
import com.atweibo.ecommerce.common.TableId;
import com.atweibo.ecommerce.service.IAddressService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Description
 * @Author weibo
 * @Data 2022/11/20 15:32
 */
@RestController
@Slf4j
@Api(tags = "用户地址服务")
@RequestMapping("/address")
public class AddressController {

    @Autowired
    private IAddressService addressService;


    @ApiOperation(value = "创建",notes = "创建用户地址信息",httpMethod = "POST")
    @PostMapping("/createAddress")
    public TableId createAddressInfo(@RequestBody AddressInfo addressInfo){
        return addressService.cteateAddressInfo(addressInfo);
    }


    @ApiOperation(value = "当前用户",notes = "获取当前用户地址信息",httpMethod = "GET")
    @GetMapping("/currentAddressInfo")
    public AddressInfo getCurrentAddrssInfo(){
        return addressService.getCurentAddressInfo();
    }

    @ApiOperation(value = "获取当前用户地址信息",notes = "通过id获取当前用户地址信息，id是EcommerceAddress 表的主键",
                httpMethod = "GET")
    @GetMapping("/addressInfoById")
    public AddressInfo getAddrssInfoById(@RequestBody Long id){
        return addressService.getAddressInfoById(id);
    }

    @ApiOperation(value = "获取当前用户地址信息",notes = "通过TableId获取当前用户地址信息",
            httpMethod = "POST")
    @PostMapping("/addressInfoByTableId")
    public AddressInfo getAddressInfoByTableId(@RequestBody TableId tableId){
        return addressService.getAddressInfoByTableId(tableId);
    }


}
