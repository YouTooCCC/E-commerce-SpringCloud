package com.atweibo.ecommerce.dao;

import com.atweibo.ecommerce.entity.EcommerceAddress;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @Description
 * @Author weibo
 * @Data 2022/11/19 16:30
 */

public interface EcommerceAddressDao extends JpaRepository<EcommerceAddress,Long> {

    /*根据用户id查询地址信息*/
    List<EcommerceAddress> findAllByUserId(Long userId);

}
