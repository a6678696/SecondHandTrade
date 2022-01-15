package com.ledao.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ledao.entity.Announcement;

import java.util.List;

/**
 * 公告Service接口
 *
 * @author LeDao
 * @company
 * @create 2022-01-15 13:42
 */
public interface AnnouncementService {

    /**
     * 分页条件查询公告
     *
     * @param announcementQueryWrapper
     * @param announcementPage
     * @return
     */
    List<Announcement> list(QueryWrapper<Announcement> announcementQueryWrapper, Page<Announcement> announcementPage);

    /**
     * 不分页条件查询公告
     *
     * @param announcementQueryWrapper
     * @return
     */
    List<Announcement> list(QueryWrapper<Announcement> announcementQueryWrapper);

    /**
     * 获取记录数
     *
     * @param announcementQueryWrapper
     * @return
     */
    Integer getCount(QueryWrapper<Announcement> announcementQueryWrapper);

    /**
     * 添加公告
     *
     * @param announcement
     * @return
     */
    int add(Announcement announcement);

    /**
     * 修改公告
     *
     * @param announcement
     * @return
     */
    int update(Announcement announcement);

    /**
     * 根据id删除公告
     *
     * @param id
     * @return
     */
    int deleteById(Integer id);

    /**
     * 根据id查找公告
     *
     * @param id
     * @return
     */
    Announcement findById(Integer id);
}
