package com.atweibo.ecommerce.service.async;

import com.atweibo.ecommerce.goods.GoodsInfo;

import java.util.List;

/**
 * @Description 异步服务接口定义，  实现商品入库提供给运维使用
 * @Author weibo  taskId  标识异步任务
 * @Data 2022/11/22 15:45
 */

public interface IAsyncService {
    /**
     * 异步将商品信息保存下莱
     * */
    void asyncImportGoods(List<GoodsInfo> goodsInfos,String taskId);



}
