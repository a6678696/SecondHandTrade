package com.ledao.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ledao.entity.Announcement;
import com.ledao.entity.Goods;
import com.ledao.service.AnnouncementService;
import com.ledao.service.GoodsService;
import com.ledao.service.GoodsTypeService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

import static com.ledao.controller.IndexController.getFirstImageInGoodsContent;

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

    @Resource
    private GoodsService goodsService;

    @Resource
    private GoodsTypeService goodsTypeService;

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
        //获取推荐商品列表
        QueryWrapper<Goods> goodsQueryWrapper3 = new QueryWrapper<>();
        goodsQueryWrapper3.eq("isRecommend", 1);
        goodsQueryWrapper3.eq("state", 1);
        Page<Goods> goodsPage3 = new Page<>(1, 9);
        List<Goods> goodsRecommendList = goodsService.list(goodsPage3, goodsQueryWrapper3);
        for (Goods goods2 : goodsRecommendList) {
            getFirstImageInGoodsContent(goods2);
            goods2.setGoodsTypeName(goodsTypeService.findById(goods2.getGoodsTypeId()).getName());
        }
        Collections.shuffle(goodsRecommendList);
        mav.addObject("goodsRecommendList", goodsRecommendList);
        mav.addObject("title", announcement.getTitle() + "--LeDao校园二手交易平台");
        mav.addObject("mainPage", "page/announcementDetails");
        mav.addObject("mainPageKey", "#b");
        mav.setViewName("index");
        return mav;
    }
}
