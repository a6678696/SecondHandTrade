package com.ledao.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ledao.entity.GoodsType;
import com.ledao.mapper.GoodsTypeMapper;
import com.ledao.service.GoodsTypeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 商品类别Service接口实现类
 *
 * @author LeDao
 * @company
 * @create 2022-01-15 21:11
 */
@Service("goodsTypeService")
public class GoodsTypeServiceImpl implements GoodsTypeService {

    @Resource
    private GoodsTypeMapper goodsTypeMapper;

    @Override
    public List<GoodsType> list(Page<GoodsType> goodsTypePage, QueryWrapper<GoodsType> goodsTypeQueryWrapper) {
        return goodsTypeMapper.selectPage(goodsTypePage, goodsTypeQueryWrapper).getRecords();
    }

    @Override
    public List<GoodsType> list(QueryWrapper<GoodsType> goodsTypeQueryWrapper) {
        return goodsTypeMapper.selectList(goodsTypeQueryWrapper);
    }

    @Override
    public Integer getCount(QueryWrapper<GoodsType> goodsTypeQueryWrapper) {
        return goodsTypeMapper.selectCount(goodsTypeQueryWrapper);
    }

    @Override
    public int add(GoodsType goodsType) {
        return goodsTypeMapper.insert(goodsType);
    }

    @Override
    public int update(GoodsType goodsType) {
        return goodsTypeMapper.updateById(goodsType);
    }

    @Override
    public int deleteById(Integer id) {
        return goodsTypeMapper.deleteById(id);
    }

    @Override
    public GoodsType findById(Integer id) {
        return goodsTypeMapper.selectById(id);
    }
}
