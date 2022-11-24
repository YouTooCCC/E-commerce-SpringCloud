package com.atweibo.ecommerce.converter;


import com.atweibo.ecommerce.constant.GoodsStatus;

import javax.persistence.AttributeConverter;

/**
 * @Description:  商品状态枚举属性转换器
 * @Author weibo
 * @Data 2022/11/22 11:11
 */

public class GoodsStatusConverter implements AttributeConverter<GoodsStatus,Integer> {


    /**
     * @return java.lang.Integer
     * @Describe:    转换成数据表的基本类型
     * @author Weibo
     * @date 2022/11/22 11:16
     */

    @Override
    public Integer convertToDatabaseColumn(GoodsStatus goodsStatus) {

        return goodsStatus.getStatus();
    }

    /**
     * @return com.atweibo.ecommerce.constant.GoodsStatus
     * @Describe:       还原数据表中的字段到Java的数据类型
     * @author Weibo
     * @date 2022/11/22 11:17
     */

    @Override
    public GoodsStatus convertToEntityAttribute(Integer status) {
        return  GoodsStatus.of(status);
    }
}
