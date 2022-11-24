package com.atweibo.ecommerce.dao;

import com.atweibo.ecommerce.entity.EcommerceUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface EcommerceUserDao extends JpaRepository<EcommerceUser,Long> {
    /*根据用户名查询对象
     * select * form t_ecommerce_user where username = ?
     * */
    EcommerceUser findByUsername(String username);

    /*根据用户名查询对象
     * select * form t_ecommerce_user where username = ? and password = ?
     * */
    EcommerceUser  findByUsernameAndPassword(@Param("username") String username, @Param("password") String password);

    /*  EcommerUser save(String username,String password,String extraInfo);*/
}
