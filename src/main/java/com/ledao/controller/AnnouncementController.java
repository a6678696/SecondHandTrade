package com.ledao.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ledao.entity.Announcement;
import com.ledao.service.AnnouncementService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;

/**
 * 前台公告Controller层
 *
 * @author LeDao
 * @company
 * @create 2022-01-15 14:43
 */
@Controller
@RequestMapping("/announcement")
public class AnnouncementController {

    @Resource
    private AnnouncementService announcementService;

    /**
     * 查看公告详情
     *
     * @param id
     * @return
     */
    @RequestMapping("/{id}")
    public ModelAndView details(@PathVariable("id") Integer id) {
        ModelAndView mav = new ModelAndView();
        //要展示的公告
        Announcement announcement = announcementService.findById(id);
        announcement.setClick(announcement.getClick() + 1);
        mav.addObject("announcement", announcement);
        announcementService.update(announcement);
        //获取公告list
        QueryWrapper<Announcement> announcementQueryWrapper = new QueryWrapper<>();
        announcementQueryWrapper.orderByAsc("sortNum");
        List<Announcement> announcementList = announcementService.list(announcementQueryWrapper);
        for (int i = 0; i < announcementList.size(); i++) {
            if (announcementList.get(i).equals(announcement)) {
                announcementList.remove(announcement);
                i--;
            }
        }
        mav.addObject("announcementList", announcementList);
        mav.addObject("title", announcement.getTitle() + "--LeDao校园二手交易平台");
        mav.addObject("mainPage", "page/announcementDetails");
        mav.addObject("mainPageKey", "#b");
        mav.setViewName("index");
        return mav;
    }
}
