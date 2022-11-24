package com.atweibo.ecommerce.controller;

import com.atweibo.ecommerce.goods.GoodsInfo;
import com.atweibo.ecommerce.service.async.AsyncTaskManager;
import com.atweibo.ecommerce.vo.AsyncTaskInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Description 异步任务服务对外提供的 接口
 * @Author weibo
 * @Data 2022/11/23 14:16
 */
@Api(tags = "商品异步入库服务")
@RestController
@Slf4j
@RequestMapping("/asyncGoods")
public class AsyncGoodsController {
    @Autowired
    private AsyncTaskManager asyncTaskManager;


    @PostMapping("/importGoods")
    @ApiOperation(value = "导入商品",notes = "导入商品进入到商品表",httpMethod = "POST")
    public AsyncTaskInfo importGoods(@RequestBody List<GoodsInfo> goodsInfos){
        return asyncTaskManager.submit(goodsInfos);
    }
    @GetMapping("/taskInfo")
    @ApiOperation(value = "查询状态",notes = "查询异步任务的执行状态",httpMethod = "GET")
    public AsyncTaskInfo getTaskInfo(@RequestParam String taskId){
        return asyncTaskManager.getTaskInfo(taskId);
    }
}
