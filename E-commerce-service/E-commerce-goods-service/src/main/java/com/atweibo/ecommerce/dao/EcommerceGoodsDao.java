package com.atweibo.ecommerce.dao;

import com.atweibo.ecommerce.constant.BrandCategory;
import com.atweibo.ecommerce.constant.GoodsCategory;
import com.atweibo.ecommerce.entity.EcommerceGoods;
import org.bouncycastle.jcajce.provider.asymmetric.ec.KeyFactorySpi;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

/**
 * @Description 继承自PagingAndSortingRepository可以使用分页和排序功能
 * @Author weibo
 * @Data 2022/11/22 14:09
 */

public interface EcommerceGoodsDao extends PagingAndSortingRepository<EcommerceGoods,Long> {
    /*
    *根据查询条件查询商品表，并返回限制结果

    select *
    from t_ecommerce_goods
    where goods_category = ? and brand_category = ? and goods_name = ?
    limit 1;

    * First1
    * */
    Optional<EcommerceGoods> findFirst1ByGoodsCategoryAndBrandCategoryAndGoodsName(
            GoodsCategory goodsCategory, BrandCategory brandCategory,String goodsName
            );

}
