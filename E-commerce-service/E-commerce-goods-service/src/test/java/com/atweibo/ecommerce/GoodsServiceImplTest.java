package com.atweibo.ecommerce;


import com.alibaba.fastjson.JSON;
import com.atweibo.ecommerce.common.TableId;
import com.atweibo.ecommerce.goods.DeductGoodsInventory;
import com.atweibo.ecommerce.service.IGoodsService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description
 * @Author weibo
 * @Data 2022/11/25 14:42
 */
@SpringBootTest
@Slf4j
@RunWith(SpringRunner.class)//让spring容器先运行起来，再运行测试
public class GoodsServiceImplTest {

   @Resource
   private IGoodsService goodsService;

    @Test
    public void testGetGoodsInfobuTableId(){
        List<Long> ids = Arrays.asList(15L, 16L, 17L);
        List<TableId.Id> tIds = ids.stream().map(TableId.Id::new).collect(Collectors.toList());
        log.info("测试用例：{}", JSON.toJSONString(goodsService.getGoodsInfoByTableId(new TableId(tIds))));
    }


    @Test
    public void testPageGoodsInfo(){
        System.out.println(goodsService.getSimpleGoodsInfoByPage(1));
    }



    @Test
    public void testDeduGoods(){
        List<DeductGoodsInventory> deductGoodsInventories = Arrays.asList(new DeductGoodsInventory(16L, 100)
        );

        System.out.println(goodsService.deductGoodsInventory(deductGoodsInventories));


    }

}
