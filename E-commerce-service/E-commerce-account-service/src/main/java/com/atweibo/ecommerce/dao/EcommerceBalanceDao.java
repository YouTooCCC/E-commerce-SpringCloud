package com.atweibo.ecommerce.dao;


import com.atweibo.ecommerce.entity.EcommerceBalance;
import org.springframework.data.jpa.repository.JpaRepository;


public interface EcommerceBalanceDao extends JpaRepository<EcommerceBalance,Long> {

    /*根据userId查询 EcommerceBalance对象*/
   EcommerceBalance findByUserId(Long userId);
}
