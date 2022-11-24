package com.atweibo.ecommerce.service.impl;

import com.alibaba.fastjson.JSON;
import com.atweibo.ecommerce.common.TableId;
import com.atweibo.ecommerce.constant.GoodsConstant;
import com.atweibo.ecommerce.dao.EcommerceGoodsDao;
import com.atweibo.ecommerce.entity.EcommerceGoods;
import com.atweibo.ecommerce.goods.DeductGoodsInventory;
import com.atweibo.ecommerce.goods.GoodsInfo;
import com.atweibo.ecommerce.goods.SimpleGoodsInfo;
import com.atweibo.ecommerce.service.IGoodsService;
import com.atweibo.ecommerce.vo.PageSimpleGoodsInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.IterableUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 商品微服务功能实现
 *
 * */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class GoodsServiceImpl implements IGoodsService {

    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private EcommerceGoodsDao ecommerceGoodsDao;


    @Override
    public List<GoodsInfo> getGoodsInfoByTableId(TableId tableId) {

        /*详细的商品信息，不能从 redis 缓存中拿到*/
        List<Long> ids = tableId.getIds().stream()
                .map(TableId.Id::getId)
                .collect(Collectors.toList());

        log.info("get goods info by ids: {}", JSON.toJSONString(ids));

        List<EcommerceGoods> ecommerceGoods = IterableUtils.toList(
                ecommerceGoodsDao.findAllById(ids)
        );
        return ecommerceGoods.stream()
                .map(EcommerceGoods::toGoodsInfo).collect(Collectors.toList());
    }

    @Override
    public PageSimpleGoodsInfo getSimpleGoodsInfoByPage(int page) {

        // 分页不能从 redis cache 中去拿
        if (page <= 1) {
            page = 1;   // 默认是第一页
        }

        // 这里分页的规则(可以自由修改): 1页10条数据, 按照 id 倒序排列
        Pageable pageable = PageRequest.of(
                page - 1, 10, Sort.by("id").descending());

        Page<EcommerceGoods> orderPage = ecommerceGoodsDao.findAll(pageable);

        // 是否还有更多页: 总页数是否大于当前给定的页
        boolean hasMore = orderPage.getTotalPages() > page;

        return new PageSimpleGoodsInfo(
                orderPage.getContent().stream()
                        .map(EcommerceGoods::toSimple).collect(Collectors.toList()),
                hasMore
        );
    }

    /**
     *获取商品的简单信息，可以从 Redis cache中拿，
     *拿不到就需要从DB中拿 并保存到Redis里面
     */
    @Override
    public List<SimpleGoodsInfo> getSimpleGoodsInfoByTableId(TableId tableId) {
        /*获取商品的简单信息，可以从 Redis cache中拿，
        * 拿不到就需要从DB中拿 并保存到Redis里面*/
        //将tableId的id都转换为String，因为Redis中的kv键值对都是String类型的
        List<Object>  goodIds= tableId.getIds().stream()
                .map(i->i.getId().toString())
                .collect(Collectors.toList());
        //从redis中获取简单商品信息
        List<Object> cacheSimpleGoodsInfos = redisTemplate.opsForHash()
                .multiGet(GoodsConstant.ECOMMERCE_GOODS_DICT_KEY, goodIds);

        //如果从redis中查询到了商品信息，分两种情况去操作
        if(CollectionUtils.isEmpty(cacheSimpleGoodsInfos)){
            /*1. 如果从缓存中查询到了 所需要的 SimpleGoodsInfo*/
            if (cacheSimpleGoodsInfos.size() == goodIds.size()){
                log.info("get simple goods info by ids (from cache) :{}",
                        JSON.toJSONString(goodIds));
                return parseCacheGoodsInfo(cacheSimpleGoodsInfos);
            }else {
                /*2.一半从数据表中获取(right)，一半从redis cache中获得(left)*/
                List<SimpleGoodsInfo> left = parseCacheGoodsInfo(cacheSimpleGoodsInfos);
                /*去差集：传递进来的参数 - 缓存中查到的  =  缓存中没有的  subtract差集方法*/
                Collection<Long> subtractIds = CollectionUtils.subtract(
                        goodIds.stream().map(g -> Long.valueOf(g.toString())).collect(Collectors.toList()),
                        left.stream().map(SimpleGoodsInfo::getId).collect(Collectors.toList())
                );

                /*缓存中没有的，查询数据表并缓存*/
                List<SimpleGoodsInfo> right = queryGoodsFromDBAndCacheToRedis(
                        new TableId(subtractIds
                                .stream()
                                .map(TableId.Id::new)
                                .collect(Collectors.toList())));
                //合并left 和right并返回
                log.info("get simple goods info by ids (from DB" +
                        " and cache):{}",JSON.toJSONString(subtractIds));
                return new ArrayList<>(CollectionUtils.union(left, right));
             }
        }else {
            /*从reids里面什么都没查到*/
            return queryGoodsFromDBAndCacheToRedis(tableId);
        }
    }

    @Override
    public Boolean deductGoodsInventory(List<DeductGoodsInventory> deductGoodsInventories) {
        //检查参数是否合法
        deductGoodsInventories.forEach(d->{
            if(d.getCount()<=0){
                throw new RuntimeException("purchase goods count need >0");
            }
        });

        List<EcommerceGoods> ecommerceGoods = IterableUtils.toList(
                ecommerceGoodsDao.findAllById(deductGoodsInventories.stream()
                        .map(DeductGoodsInventory::getGoodsId)
                        .collect(Collectors.toList())));

        //根据传递的goodsIds查询不到商品对象，抛异常
        if(CollectionUtils.isEmpty(ecommerceGoods)){
            throw new RuntimeException("Can not found and goods by request！");

        }
        //查询出来的商品数量与传递的不一致，抛异常
        if (ecommerceGoods.size() != deductGoodsInventories.size()){
            throw new RuntimeException("request is not valid");
        }
        // goodsId 映射 DeductGoodsInventory

        Map<Long, DeductGoodsInventory> goodsId2Inventory =
                deductGoodsInventories.stream()
                        .collect(Collectors
                                .toMap(DeductGoodsInventory::getGoodsId, Function.identity()));

        //检查是不是可以扣减库存，再去扣减 库存
        ecommerceGoods.forEach(g->{
            Long currentInventory = g.getInventory();
            Integer needDeductInventory = goodsId2Inventory.get(g.getId()).getCount();
            if (currentInventory < needDeductInventory){
                log.error("goods inventroy is not enough:{},{}",
                        currentInventory,needDeductInventory);
                throw new RuntimeException("goods inventory is not enoug:"+g.getId());
            }
            //扣减库存
            g.setInventory(currentInventory - needDeductInventory);
            log.info("deduct goods inventory:{},{},{}",g.getId(),currentInventory,g.getInventory());
        });
        ecommerceGoodsDao.saveAll(ecommerceGoods);
        log.info("deduct goods inventory done");
        return true;
    }

    /** 将缓存中的数据反序列化成Java pojo对象     工具*/
    private List<SimpleGoodsInfo> parseCacheGoodsInfo(List<Object> cachedSimpleGoodsInfo){
        return cachedSimpleGoodsInfo.stream()
                .map(s -> JSON.parseObject(s.toString(),SimpleGoodsInfo.class))
                .collect(Collectors.toList());
    }
    /** 从数据表中查询数据，并缓存到redis中*/
    private List<SimpleGoodsInfo> queryGoodsFromDBAndCacheToRedis(TableId tableId){
        //从数据表中查询数据并转换成long类型的
        List<Long> ids = tableId.getIds().stream()
                .map(TableId.Id::getId)
                .collect(Collectors.toList());
        log.info("get simple goods info by ids (from DB):{}",JSON.toJSONString(ids));

       List<EcommerceGoods> ecommerceGoods = IterableUtils.toList(
                ecommerceGoodsDao.findAllById(ids));
       //转换为SimpleGoods对象
       List<SimpleGoodsInfo> result = ecommerceGoods.stream()
                .map(EcommerceGoods::toSimple).collect(Collectors.toList());

        /*将结果缓存，下一次可以直接从redis 缓存中查询*/
        log.info("cache goods info:{}", JSON.toJSONString(ids));

        Map<String, String> id2JsonObject = new HashMap<>(result.size());
        result.forEach(g->id2JsonObject.put(g.getId().toString(), JSON.toJSONString(g)));

        /*保存到redis 中*/
        redisTemplate.opsForHash().putAll(GoodsConstant.ECOMMERCE_GOODS_DICT_KEY, id2JsonObject);
        return result;

    }

}
