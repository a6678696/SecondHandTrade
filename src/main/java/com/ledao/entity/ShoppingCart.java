package com.ledao.entity;

import lombok.Data;

import java.util.List;

/**
 * 购物车实体类
 *
 * @author LeDao
 * @company
 * @create 2022-01-17 1:23
 */
@Data
public class ShoppingCart {

    /**
     * 用户id
     */
    private Integer userId;
    /**
     * 购物车商品列表
     */
    private List<Goods> goodsShoppingCartList;
}
