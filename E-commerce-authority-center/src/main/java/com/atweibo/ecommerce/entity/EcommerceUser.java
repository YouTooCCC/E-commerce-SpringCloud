package com.atweibo.ecommerce.entity;




import lombok.AllArgsConstructor;

import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @Description
 * @Author weibo
 * @Data 2022/11/10 14:23
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "t_ecommerce_user")
public class EcommerceUser implements Serializable {
    /*ID*//*
    private static final long serialVersionUID = 1L;*/


    /*主键自增*/
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id",nullable = false)
    private Long id;


    /*用户名*/
    @Column(name = "username",nullable = false)
    private String username;
    /*密码*/
    @Column(name = "password",nullable = false)
    private String password;

    /*额外的信息，json*/
    @Column(name = "extra_info",nullable = false)
    private String extraInfo;

    /*创建时间*/
    @Column(name = "create_time",nullable = false)
    @CreatedDate
    private Date createTime;

    /*更新时间*/
    @Column(name = "update_time",nullable = false)
    @LastModifiedDate
    private Date updateTime;

   }
