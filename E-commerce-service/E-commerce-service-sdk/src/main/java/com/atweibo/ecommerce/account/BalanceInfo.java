package com.atweibo.ecommerce.account;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description 用户账户余额信息
 * @Author weibo
 * @Data 2022/11/19 17:14
 */
@ApiModel(description = "用户账余额信息")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BalanceInfo {
    @ApiModelProperty(value = "用户主键id")
    private Long userId;

    @ApiModelProperty(value = "用户账户余额")
    private Long Balance;

}
