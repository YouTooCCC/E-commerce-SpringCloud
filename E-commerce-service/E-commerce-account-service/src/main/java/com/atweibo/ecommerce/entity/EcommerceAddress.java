package com.atweibo.ecommerce.entity;

import com.atweibo.ecommerce.account.AddressInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

/**
 * @Description
 * @Author weibo
 * @Data 2022/11/19 15:25
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "t_ecommerce_address")
public class EcommerceAddress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",nullable = false)
    private Long id;

    @Column(name = "user_id",nullable = false)
    private Long userId;

    @Column(name = "username",nullable = false)
    private String username;

    @Column(name = "phone",nullable = false)
    private String phone;

    @Column(name = "province",nullable = false)
    private String province;

    @Column(name = "city",nullable = false)
    private String city;

    @Column(name = "address_detail",nullable = false)
    private String addressDetail;

    @CreatedDate
    @Column(name = "create_time",nullable = false)
    private Date createTime;

    @LastModifiedDate
    @Column(name = "update_time",nullable = false)
    private Date updateTime;

    /*根据userId +AddressItem 得到EcommerceAddress*/
    public static EcommerceAddress to(Long id, AddressInfo.AddressItem addressItem){
        EcommerceAddress ecommerceAddress = new EcommerceAddress();
        ecommerceAddress.setUserId(id);
        ecommerceAddress.setUsername(addressItem.getUsername());
        ecommerceAddress.setPhone(addressItem.getPhone());
        ecommerceAddress.setProvince(addressItem.getProvince());
        ecommerceAddress.setCity(addressItem.getCity());
        ecommerceAddress.setAddressDetail(addressItem.getAddressDetail());
        return ecommerceAddress;
    }

    public AddressInfo.AddressItem toAddressItem(){
        AddressInfo.AddressItem addressItem = new AddressInfo.AddressItem();

        addressItem.setId(this.id);
        addressItem.setUsername(this.username);
        addressItem.setPhone(this.phone);
        addressItem.setProvince(this.province);
        addressItem.setCity(this.city);
        addressItem.setCteateTime(this.createTime);
        addressItem.setUpdateTime(this.updateTime);

        return addressItem;
    }

}
