package com.atweibo.ecommerce.converter;

import com.atweibo.ecommerce.constant.GoodsCategory;

import javax.persistence.AttributeConverter;

/**
 * @Description
 * @Author weibo
 * @Data 2022/11/22 13:34
 */

public class GoodsCategoryConverter implements AttributeConverter<GoodsCategory,String> {
    @Override
    public String convertToDatabaseColumn(GoodsCategory goodsCategory) {
        return goodsCategory.getCode();
    }

    @Override
    public GoodsCategory convertToEntityAttribute(String code) {
        return GoodsCategory.of(code);
    }
}
