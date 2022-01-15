package com.ledao.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ledao.entity.Announcement;
import com.ledao.service.AnnouncementService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 后台公告Controller层
 *
 * @author LeDao
 * @company
 * @create 2022-01-15 13:51
 */
@RestController
@RequestMapping("/admin/announcement")
public class AnnouncementAdminController {

    @Resource
    private AnnouncementService announcementService;

    /**
     * 分页条件查询公告
     *
     * @param announcement
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping("/list")
    public Map<String, Object> list(Announcement announcement, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "rows", required = false) Integer rows) {
        Map<String, Object> resultMap = new HashMap<>(16);
        QueryWrapper<Announcement> announcementQueryWrapper = new QueryWrapper<>();
        if (announcement.getTitle() != null) {
            announcementQueryWrapper.like("title", announcement.getTitle());
        }
        announcementQueryWrapper.orderByAsc("sortNum");
        Page<Announcement> announcementPage = new Page<>(page, rows);
        List<Announcement> announcementList = announcementService.list(announcementQueryWrapper, announcementPage);
        Integer total = announcementService.getCount(announcementQueryWrapper);
        resultMap.put("rows", announcementList);
        resultMap.put("total", total);
        return resultMap;
    }

    /**
     * 添加或修改公告
     *
     * @param announcement
     * @return
     */
    @RequestMapping("/save")
    public Map<String, Object> save(Announcement announcement) {
        Map<String, Object> resultMap = new HashMap<>(16);
        int key;
        if (announcement.getId() == null) {
            announcement.setTime(new Date());
            announcement.setClick(0);
            key = announcementService.add(announcement);
        } else {
            key = announcementService.update(announcement);
        }
        if (key > 0) {
            resultMap.put("success", true);
        } else {
            resultMap.put("success", false);
        }
        return resultMap;
    }

    /**
     * 删除公告,可批量删除
     *
     * @param ids
     * @return
     */
    @RequestMapping("/delete")
    public Map<String, Object> delete(String ids) {
        Map<String, Object> resultMap = new HashMap<>(16);
        String[] idsArr = ids.split(",");
        int key = 0;
        for (int i = 0; i < idsArr.length; i++) {
            Integer id = Integer.valueOf(idsArr[i]);
            announcementService.deleteById(id);
            key++;
        }
        if (key > 0) {
            resultMap.put("success", true);
        } else {
            resultMap.put("success", false);
        }
        return resultMap;
    }
}
