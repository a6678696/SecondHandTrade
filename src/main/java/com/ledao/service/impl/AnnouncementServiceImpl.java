package com.ledao.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ledao.entity.Announcement;
import com.ledao.mapper.AnnouncementMapper;
import com.ledao.service.AnnouncementService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 公告Service接口实现类
 *
 * @author LeDao
 * @company
 * @create 2022-01-15 13:42
 */
@Service("announcementService")
public class AnnouncementServiceImpl implements AnnouncementService {

    @Resource
    private AnnouncementMapper announcementMapper;

    @Override
    public List<Announcement> list(QueryWrapper<Announcement> announcementQueryWrapper, Page<Announcement> announcementPage) {
        return announcementMapper.selectPage(announcementPage, announcementQueryWrapper).getRecords();
    }

    @Override
    public List<Announcement> list(QueryWrapper<Announcement> announcementQueryWrapper) {
        return announcementMapper.selectList(announcementQueryWrapper);
    }

    @Override
    public Integer getCount(QueryWrapper<Announcement> announcementQueryWrapper) {
        return announcementMapper.selectCount(announcementQueryWrapper);
    }

    @Override
    public int add(Announcement announcement) {
        return announcementMapper.insert(announcement);
    }

    @Override
    public int update(Announcement announcement) {
        return announcementMapper.updateById(announcement);
    }

    @Override
    public int deleteById(Integer id) {
        return announcementMapper.deleteById(id);
    }

    @Override
    public Announcement findById(Integer id) {
        return announcementMapper.selectById(id);
    }
}
