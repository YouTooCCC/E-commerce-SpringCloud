package com.atweibo.ecommerce.converter;

import com.atweibo.ecommerce.constant.BrandCategory;

import javax.persistence.AttributeConverter;

/**
 * @Description 品牌分类枚举属性转换器
 * @Author weibo
 * @Data 2022/11/22 13:24
 */

public class BrandCategoryConverter implements AttributeConverter<BrandCategory,String> {


    @Override
    public String convertToDatabaseColumn(BrandCategory brandCategory) {

        return brandCategory.getCode();
    }

    @Override
    public BrandCategory convertToEntityAttribute(String code) {
        return BrandCategory.of(code);
    }
}
