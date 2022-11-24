package com.atweibo.ecommerce.account;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * @Description   用户地址信息
 * @Author weibo
 * @Data 2022/11/19 15:48
 */
@ApiModel(description = "用户地址信息")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class AddressInfo {
    @ApiModelProperty(value = "地址所属用户id")
    private long userid;

    @ApiModelProperty(value = "地址详细信息")
    private List<AddressItem> addressItems;

    /*单个的地址信息*/
    @ApiModel(description = "单个的用户地址信息")
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AddressItem {
        @ApiModelProperty(value = "地址表主键id")
        private Long id;

        @ApiModelProperty(value = "用户姓名")
        private String username;

        @ApiModelProperty(value = "电话号码")
        private String phone;

        @ApiModelProperty(value = "省份")
        private String province;

        @ApiModelProperty(value = "市")
        private String city;

        @ApiModelProperty(value = "详细地址")
        private String addressDetail;

        @ApiModelProperty(value = "创建时间")
        private Date cteateTime;

        @ApiModelProperty(value = "更新时间")
        private Date updateTime;

        public AddressItem(Long id) {
            this.id = id;
        }

        /*将AddressItem 转化成UserAddress*/
        public UserAddress toUserAddress(){
            UserAddress userAddress = new UserAddress();
            userAddress.setUsername(this.username);
            userAddress.setPhone(this.phone);
            userAddress.setProvince(this.province);
            userAddress.setCity(this.city);
            userAddress.setAddressDetail(this.addressDetail);

            return userAddress;
        }


    }
}
