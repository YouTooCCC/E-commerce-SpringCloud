package com.atweibo.ecommerce.constant;


import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;
import java.util.stream.Stream;

/**
 * @Description: 商品  品牌分类
 * @Author weibo
 * @Data 2022/11/22 11:02
 */
@Getter
@AllArgsConstructor
public enum BrandCategory {

    BRAND_A("20001","品牌A"),
    BRAND_B("20002","品牌B"),
    BRAND_C("20003","品牌C"),
    BRAND_D("20004","品牌D"),
    BRAND_F("20005","品牌E"),
    BRAND_G("20006","品牌F"),;

    /*品牌分类编码*/
    private final String code;

    /*品牌分类描述信息*/
    private final  String description;

    public static BrandCategory of(String code){
        Objects.requireNonNull(code);
        return Stream.of(values())
                .filter(bean ->bean.code.equals(code))
                .findAny()
                .orElseThrow(
                        () -> new IllegalArgumentException(code + "not exists")
                );
    }
}
