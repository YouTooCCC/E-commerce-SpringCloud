package com.atweibo.ecommerce.entity;

import com.alibaba.fastjson.JSON;
import com.atweibo.ecommerce.constant.BrandCategory;
import com.atweibo.ecommerce.constant.GoodsCategory;
import com.atweibo.ecommerce.constant.GoodsStatus;
import com.atweibo.ecommerce.converter.BrandCategoryConverter;
import com.atweibo.ecommerce.converter.GoodsCategoryConverter;
import com.atweibo.ecommerce.converter.GoodsStatusConverter;
import com.atweibo.ecommerce.goods.GoodsInfo;
import com.atweibo.ecommerce.goods.SimpleGoodsInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

/**
 * @Description
 * @Author weibo
 * @Data 2022/11/22 13:46
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = " t_ecommerce_goods")
public class EcommerceGoods
{
    /**自增主键*/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",nullable = false)
    private Long id;

    /** 商品类型*/
    @Convert(converter = GoodsCategoryConverter.class)
    @Column(name = "goods_category",nullable = false)
    private GoodsCategory goodsCategory;

    /** 品牌分类*/
    @Convert(converter = BrandCategoryConverter.class)
    @Column(name = "brand_category",nullable = false)
    private BrandCategory brandCategory;

    /** 商品名称*/
    @Column(name = "goods_name",nullable = false)
    private String goodsName;


    /** 商品照片*/
    @Column(name = "goods_pic",nullable = false)
    private String goodsPic;

    /** 商米描述*/
    @Column(name = "goods_description",nullable = false)
    private String goodsDescription;

    /*商品状态*/

    @Column(name = "goods_status",nullable = false)
    @Convert(converter = GoodsStatusConverter.class)
    private GoodsStatus goodsStatus;

    /**商品价格*/
    @Column(name = "price",nullable = false)
    private Integer price;

    /** 总供应量*/
    @Column(name = "supply",nullable = false)
    private Long supply;

    /** 库存*/
    @Column(name = "inventory",nullable = false)
    private Long inventory;

    /** 商品属性 */
    @Column(name = " goods_property",nullable = false)
    private String goodsProperty;

    /** 创建时间*/
    @CreatedDate
    @Column(name = " create_time",nullable = false)
    private Date createTime;

    /** 更新时间*/
    @LastModifiedDate
    @Column(name = "update_time",nullable = false)
    private Date updateTime;

    /**
     * <h2>将 GoodsInfo 转成实体对象</h2>
     * */
    public static EcommerceGoods to(GoodsInfo goodsInfo) {

        EcommerceGoods ecommerceGoods = new EcommerceGoods();

        ecommerceGoods.setGoodsCategory(GoodsCategory.of(goodsInfo.getGoodsCategory()));
        ecommerceGoods.setBrandCategory(BrandCategory.of(goodsInfo.getBrandCategory()));
        ecommerceGoods.setGoodsName(goodsInfo.getGoodsName());
        ecommerceGoods.setGoodsPic(goodsInfo.getGoodsPic());
        ecommerceGoods.setGoodsDescription(goodsInfo.getGoodsDescription());
        ecommerceGoods.setGoodsStatus(GoodsStatus.ONLINE);  // 可以增加一个审核的过程
        ecommerceGoods.setPrice(goodsInfo.getPrice());
        ecommerceGoods.setSupply(goodsInfo.getSupply());
        ecommerceGoods.setInventory(goodsInfo.getSupply());
        ecommerceGoods.setGoodsProperty(
                JSON.toJSONString(goodsInfo.getGoodsProperty())
        );

        return ecommerceGoods;
    }

    /**
     * <h2>将实体对象转成 GoodsInfo 对象</h2>
     * */
    public GoodsInfo toGoodsInfo() {

        GoodsInfo goodsInfo = new GoodsInfo();

        goodsInfo.setId(this.id);
        goodsInfo.setGoodsCategory(this.goodsCategory.getCode());
        goodsInfo.setBrandCategory(this.brandCategory.getCode());
        goodsInfo.setGoodsName(this.goodsName);
        goodsInfo.setGoodsPic(this.goodsPic);
        goodsInfo.setGoodsDescription(this.goodsDescription);
        goodsInfo.setGoodsStatus(this.goodsStatus.getStatus());
        goodsInfo.setPrice(this.price);
        goodsInfo.setGoodsProperty(
                JSON.parseObject(this.goodsProperty, GoodsInfo.GoodsProperty.class)
        );
        goodsInfo.setSupply(this.supply);
        goodsInfo.setInventory(this.inventory);
        goodsInfo.setCreateTime(this.createTime);
        goodsInfo.setUpdateTime(this.updateTime);

        return goodsInfo;
    }

    /**
     * <h2>将实体对象转成 SimpleGoodsInfo 对象</h2>
     * */
    public SimpleGoodsInfo toSimple() {

        SimpleGoodsInfo goodsInfo = new SimpleGoodsInfo();

        goodsInfo.setId(this.id);
        goodsInfo.setGoodsName(this.goodsName);
        goodsInfo.setGoodsPic(this.goodsPic);
        goodsInfo.setPrice(this.price);

        return goodsInfo;
    }
}
