package com.atweibo.ecommerce.controller;

import com.atweibo.ecommerce.common.TableId;
import com.atweibo.ecommerce.goods.DeductGoodsInventory;
import com.atweibo.ecommerce.goods.GoodsInfo;
import com.atweibo.ecommerce.goods.SimpleGoodsInfo;
import com.atweibo.ecommerce.service.IGoodsService;
import com.atweibo.ecommerce.vo.PageSimpleGoodsInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Description
 * @Author weibo
 * @Data 2022/11/25 16:07
 */
@RestController
@Api(tags = "商品微服务功能接口")
@RequestMapping("/goods")
public class GoodsController {
    @Autowired
    private IGoodsService goodsService;



    @ApiOperation(value = "详细商品信息", notes = "根据TableId查询详细商品信息",httpMethod = "POST")
    @PostMapping("/goodsInfoByTableId")
    public List<GoodsInfo> getGoodsInfoByTableId(@RequestBody TableId tableId){
        return goodsService.getGoodsInfoByTableId(tableId);
    }


    @ApiOperation(value = "简单商品信息", notes = "获取分页商品信息",httpMethod = "GET")
    @GetMapping("/simpleGoodsInfoBypage")
    public PageSimpleGoodsInfo getSimpleGoodsInfoByPage(@RequestParam(required = false,defaultValue = "1")int page){
        return goodsService.getSimpleGoodsInfoByPage(page);
    }

    @ApiOperation(value = "简单商品信息", notes = "根据tableId获取简单商品信息",httpMethod = "POST")
    @PostMapping("/simpleGoodsInfoByTableId")
    public List<SimpleGoodsInfo> getSimpleGoodsInfoByTableID(@RequestBody TableId tableId){
        return goodsService.getSimpleGoodsInfoByTableId(tableId);
    }


    @ApiOperation(value = "扣减商品库存", notes = "扣减商品库存",httpMethod = "POST")
    @PostMapping("/deductGoodsInventory")
    public Boolean deductGoodsInventory(@RequestBody List<DeductGoodsInventory> deductGoodsInventories){
        return goodsService.deductGoodsInventory(deductGoodsInventories);
    }
}
