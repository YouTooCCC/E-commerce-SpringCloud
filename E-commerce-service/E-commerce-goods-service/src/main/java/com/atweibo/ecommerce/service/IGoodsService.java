package com.atweibo.ecommerce.service;

import com.atweibo.ecommerce.common.TableId;
import com.atweibo.ecommerce.goods.DeductGoodsInventory;
import com.atweibo.ecommerce.goods.GoodsInfo;
import com.atweibo.ecommerce.goods.SimpleGoodsInfo;
import com.atweibo.ecommerce.vo.PageSimpleGoodsInfo;

import java.util.List;

/**
* 商品微服务接口
* */
public interface IGoodsService {
    /**
    * 根据TableId查询到商品详细信息
    * */
    List<GoodsInfo> getGoodsInfoByTableId(TableId tableId);

    /**
     * 获取分页的商品信息
     * */
    PageSimpleGoodsInfo getSimpleGoodsInfoByPage(int page);

    /**
     * 根据TableId查询到简单商品信息
     * */
    List<SimpleGoodsInfo> getSimpleGoodsInfoByTableId(TableId tableId);

    /**
     * 扣减商品库存
     * */
    Boolean deductGoodsInventory(List<DeductGoodsInventory> deductGoodsInventories);
}
