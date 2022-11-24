package com.atweibo.ecommerce.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.nacos.common.utils.CollectionUtils;
import com.atweibo.ecommerce.account.AddressInfo;
import com.atweibo.ecommerce.common.TableId;
import com.atweibo.ecommerce.dao.EcommerceAddressDao;
import com.atweibo.ecommerce.entity.EcommerceAddress;
import com.atweibo.ecommerce.filter.AccessContext;
import com.atweibo.ecommerce.service.IAddressService;
import com.atweibo.ecommerce.vo.LoginUserInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description
 * @Author weibo
 * @Data 2022/11/19 18:58
 */
@Service
@Transactional
@Slf4j
public class AddressServiceImpl implements IAddressService {
    @Autowired
    private EcommerceAddressDao addressDao;


    /*存储多个地址信息*/
    @Override
    public TableId cteateAddressInfo(AddressInfo addressInfo) {
        /*不能直接从参数中获取用户的id信息*/
        LoginUserInfo loginUserInfo = AccessContext.getLoginUserInfo();

        /*将传递的参数转换成实体对象*/
        List<EcommerceAddress> ecommerceAddresses = addressInfo.getAddressItems().stream().map(a -> EcommerceAddress.to(loginUserInfo.getId(), a))
                .collect(Collectors.toList());

        /*保存到数据表并把返回记录的id 传递给调用方*/
        List<EcommerceAddress> saveRecords = addressDao.saveAll(ecommerceAddresses);

        List<Long> ids = saveRecords.stream().map(EcommerceAddress::getId).collect(Collectors.toList());
        log.info("create address info:【{}】",loginUserInfo.getId());


        return new TableId(ids.stream().map(TableId.Id::new).collect(Collectors.toList()));
    }

    @Override
    public AddressInfo getCurentAddressInfo() {
        LoginUserInfo loginUserInfo = AccessContext.getLoginUserInfo();
        /*根据userId查询到查询地址信息，再实现转换*/
        List<EcommerceAddress> ecommerceAddresses = addressDao.findAllByUserId(loginUserInfo.getId());
        List<AddressInfo.AddressItem> addressItems = ecommerceAddresses.stream().map(EcommerceAddress::toAddressItem).collect(Collectors.toList());

        return new AddressInfo(loginUserInfo.getId(),addressItems);
    }

    @Override
    public AddressInfo getAddressInfoById(Long id) {
        /*存在就返回，不存在就返回null*/
        EcommerceAddress ecommerceAddress = addressDao.findById(id).orElse(null);
        if(ecommerceAddress == null){
            throw new RuntimeException("address is not exist");
        }

        return new AddressInfo(ecommerceAddress.getUserId(),
                Collections.singletonList(ecommerceAddress.toAddressItem()));
    }

    @Override
    public AddressInfo getAddressInfoByTableId(TableId tableId) {

        List<Long> ids = tableId.getIds().stream().map(TableId.Id::getId).collect(Collectors.toList());

        log.info("get address info by table id: 【{}】", JSON.toJSONString(ids));
        List<EcommerceAddress> ecommerceAddresses = addressDao.findAllById(ids);
        if (CollectionUtils.isEmpty(ecommerceAddresses)){
            return  new AddressInfo(-1L,Collections.emptyList());
        }
        List<AddressInfo.AddressItem> addressItems =
                ecommerceAddresses.stream()
                        .map(EcommerceAddress::toAddressItem)
                        .collect(Collectors.toList());

        return new AddressInfo(
                ecommerceAddresses.get(0).getUserId(),addressItems);

    }
}
