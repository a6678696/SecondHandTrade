package com.ledao.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ledao.entity.Goods;
import com.ledao.mapper.GoodsMapper;
import com.ledao.service.GoodsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 商品Service接口实现类
 *
 * @author LeDao
 * @company
 * @create 2022-01-15 22:13
 */
@Service("goodsService")
public class GoodsServiceImpl implements GoodsService {

    @Resource
    private GoodsMapper goodsMapper;

    @Override
    public List<Goods> list(Page<Goods> goodsPage, QueryWrapper<Goods> goodsQueryWrapper) {
        return goodsMapper.selectPage(goodsPage, goodsQueryWrapper).getRecords();
    }

    @Override
    public List<Goods> list(QueryWrapper<Goods> goodsQueryWrapper) {
        return goodsMapper.selectList(goodsQueryWrapper);
    }

    @Override
    public Integer getCount(QueryWrapper<Goods> goodsQueryWrapper) {
        return goodsMapper.selectCount(goodsQueryWrapper);
    }

    @Override
    public int add(Goods goods) {
        return goodsMapper.insert(goods);
    }

    @Override
    public int update(Goods goods) {
        return goodsMapper.updateById(goods);
    }

    @Override
    public int deleteById(Integer id) {
        return goodsMapper.deleteById(id);
    }

    @Override
    public Goods findById(Integer id) {
        return goodsMapper.selectById(id);
    }
}
