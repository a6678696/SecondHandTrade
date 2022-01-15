package com.ledao.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ledao.entity.Carousel;

import java.util.List;

/**
 * 轮播图Service接口
 *
 * @author LeDao
 * @company
 * @create 2022-01-13 13:21
 */
public interface CarouselService {

    /**
     * 添加轮播图
     *
     * @param carousel
     * @return
     */
    int add(Carousel carousel);

    /**
     * 修改轮播图
     *
     * @param carousel
     * @return
     */
    int update(Carousel carousel);

    /**
     * 根据id删除轮播图
     *
     * @param id
     * @return
     */
    int deleteById(Integer id);

    /**
     * 分页条件查询轮播图
     *
     * @param carouselQueryWrapper
     * @param carouselPage
     * @return
     */
    List<Carousel> list(QueryWrapper<Carousel> carouselQueryWrapper, Page<Carousel> carouselPage);

    /**
     * 条件查询轮播图
     *
     * @param carouselQueryWrapper
     * @return
     */
    List<Carousel> list(QueryWrapper<Carousel> carouselQueryWrapper);

    /**
     * 获取记录数
     *
     * @param carouselQueryWrapper
     * @return
     */
    Integer getCount(QueryWrapper<Carousel> carouselQueryWrapper);

    /**
     * 根据id查找轮播图
     *
     * @param id
     * @return
     */
    Carousel findById(Integer id);
}
