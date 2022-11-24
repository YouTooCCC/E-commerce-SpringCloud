package com.atweibo.ecommerce.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;
import java.util.stream.Stream;

/**
 * @Description: 商品  状态枚举类
 * @Author weibo
 * @Data 2022/11/22 11:02
 */
@Getter
@AllArgsConstructor
public enum GoodsStatus {

    ONLINE(101,"上线"),
    OFFLINE(102,"下线"),
    STOCK_OUT(103,"缺货"),;

    /** 状态码*/
    private final Integer status;

    /** 状态描述*/
    private final String description;

    /**
     * @return com.atweibo.ecommerce.constant.GoodStatus
     * @Describe:       根据状态码code获取到 GoodsStatus
     * @author Weibo
     * @date 2022/11/22 11:06
     */
    public static GoodsStatus of(Integer status){
        Objects.requireNonNull(status);
        return Stream.of(values())
                .filter(bean ->bean.status.equals(status))
                .findAny()
                .orElseThrow(
                        ()->new IllegalArgumentException(status + "not exists")
                );
    }
}
