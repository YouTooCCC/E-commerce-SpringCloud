package com.atweibo.ecommerce.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;
import java.util.stream.Stream;


/**
 * @Description: 商品  类别枚举类
 * @Author weibo
 * @Data 2022/11/22 11:02
 */

@Getter
@AllArgsConstructor
public enum GoodsCategory {
    DIAN_QI("10001","电器"),
    JIA_JU("10002","家具"),
    FU_SHI("10003","服饰"),
    MU_YING("10004","母婴"),
    SHI_PING("10005","食品"),
    TU_SHU("10006","图书"),;

    /*商品类别信息*/
    private final String code;

    /*商品类别描述信息*/
    private final String description;


    public static GoodsCategory of(String code){
        Objects.requireNonNull(code);
        return Stream.of(values())
                .filter(bean ->bean.code.equals(code))
                .findAny()
                .orElseThrow(
                        () ->new  IllegalArgumentException(code + "not exists")
                );
    }

}
