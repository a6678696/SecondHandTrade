package com.ledao.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ledao.entity.GoodsType;

import java.util.List;

/**
 * 商品类别Service接口
 *
 * @author LeDao
 * @company
 * @create 2022-01-15 21:10
 */
public interface GoodsTypeService {

    /**
     * 分页条件查询商品类别
     *
     * @param goodsTypePage
     * @param goodsTypeQueryWrapper
     * @return
     */
    List<GoodsType> list(Page<GoodsType> goodsTypePage, QueryWrapper<GoodsType> goodsTypeQueryWrapper);

    /**
     * 分页条件查询商品类别
     *
     * @param goodsTypePage
     * @param goodsTypeQueryWrapper
     * @return
     */
    List<GoodsType> list(QueryWrapper<GoodsType> goodsTypeQueryWrapper);

    /**
     * 获取记录数
     *
     * @param goodsTypeQueryWrapper
     * @return
     */
    Integer getCount(QueryWrapper<GoodsType> goodsTypeQueryWrapper);

    /**
     * 添加商品类别
     *
     * @param goodsType
     * @return
     */
    int add(GoodsType goodsType);

    /**
     * 修改商品类别
     *
     * @param goodsType
     * @return
     */
    int update(GoodsType goodsType);

    /**
     * 根据id删除商品类别
     *
     * @param id
     * @return
     */
    int deleteById(Integer id);

    /**
     * 根据id查找商品类别
     *
     * @param id
     * @return
     */
    GoodsType findById(Integer id);
}
