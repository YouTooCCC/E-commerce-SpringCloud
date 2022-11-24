package com.atweibo.ecommerce.goods;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description 扣减商品库存
 * @Author weibo
 * @Data 2022/11/22 15:36
 */
@ApiModel(description = "扣减商品库存对象")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeductGoodsInventory {


    @ApiModelProperty(value = "商品主键id")
    private Long goodsId;

    @ApiModelProperty(value = "扣减个数")
    private Integer count;
}
