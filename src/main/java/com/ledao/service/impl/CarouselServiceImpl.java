package com.ledao.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ledao.entity.Carousel;
import com.ledao.mapper.CarouselMapper;
import com.ledao.service.CarouselService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 轮播图Service接口实现类
 *
 * @author LeDao
 * @company
 * @create 2022-01-13 13:21
 */
@Service("carouselService")
public class CarouselServiceImpl implements CarouselService {

    @Resource
    private CarouselMapper carouselMapper;

    @Override
    public int add(Carousel carousel) {
        return carouselMapper.insert(carousel);
    }

    @Override
    public int update(Carousel carousel) {
        return carouselMapper.updateById(carousel);
    }

    @Override
    public int deleteById(Integer id) {
        return carouselMapper.deleteById(id);
    }

    @Override
    public List<Carousel> list(QueryWrapper<Carousel> carouselQueryWrapper, Page<Carousel> carouselPage) {
        return carouselMapper.selectPage(carouselPage, carouselQueryWrapper).getRecords();
    }

    @Override
    public List<Carousel> list(QueryWrapper<Carousel> carouselQueryWrapper) {
        return carouselMapper.selectList(carouselQueryWrapper);
    }

    @Override
    public Integer getCount(QueryWrapper<Carousel> carouselQueryWrapper) {
        return carouselMapper.selectCount(carouselQueryWrapper);
    }

    @Override
    public Carousel findById(Integer id) {
        return carouselMapper.selectById(id);
    }
}
