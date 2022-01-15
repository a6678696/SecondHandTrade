package com.ledao.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ledao.entity.ContactInformation;
import com.ledao.entity.Goods;
import com.ledao.entity.Goods;
import com.ledao.entity.GoodsType;
import com.ledao.service.ContactInformationService;
import com.ledao.service.GoodsService;
import com.ledao.service.GoodsTypeService;
import com.ledao.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

import static com.ledao.controller.IndexController.getFirstImageInGoodsContent;

/**
 * 前台商品Controller层
 *
 * @author LeDao
 * @company
 * @create 2022-01-15 22:20
 */
@Controller
@RequestMapping("/goods")
public class GoodsController {

    @Resource
    private GoodsService goodsService;

    @Resource
    private GoodsTypeService goodsTypeService;

    @Resource
    private UserService userService;

    @Resource
    private ContactInformationService contactInformationService;

    /**
     * 查看商品详情
     *
     * @param id
     * @return
     */
    @RequestMapping("/{id}")
    public ModelAndView details(@PathVariable("id") Integer id) {
        ModelAndView mav = new ModelAndView();
        //要展示的商品
        Goods goods = goodsService.findById(id);
        goods.setClick(goods.getClick() + 1);
        goods.setGoodsTypeName(goodsTypeService.findById(goods.getGoodsTypeId()).getName());
        goods.setUser(userService.findById(goods.getUserId()));
        QueryWrapper<ContactInformation> contactInformationQueryWrapper = new QueryWrapper<>();
        contactInformationQueryWrapper.eq("userId", goods.getUserId());
        goods.setContactInformationList(contactInformationService.list(contactInformationQueryWrapper));
        mav.addObject("goods", goods);
        goodsService.update(goods);
        //商品分类列表
        QueryWrapper<GoodsType> goodsTypeQueryWrapper = new QueryWrapper<>();
        goodsTypeQueryWrapper.orderByAsc("sortNum");
        List<GoodsType> goodsTypeList = goodsTypeService.list(goodsTypeQueryWrapper);
        for (int i = 0; i < goodsTypeList.size(); i++) {
            if ("求购".equals(goodsTypeList.get(i).getName())) {
                goodsTypeList.remove(goodsTypeList.get(i));
                i--;
            }
        }
        mav.addObject("goodsTypeList", goodsTypeList);
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
        for (int i = 0; i < goodsRecommendList.size(); i++) {
            if (goodsRecommendList.get(i).getId().equals(goods.getId())) {
                goodsRecommendList.remove(goodsRecommendList.get(i));
                i--;
            }
        }
        mav.addObject("goodsRecommendList", goodsRecommendList);
        mav.addObject("title", goods.getName() + "--LeDao校园二手交易平台");
        mav.addObject("mainPage", "page/goodsDetails");
        mav.addObject("mainPageKey", "#b");
        mav.setViewName("index");
        return mav;
    }

    /**
     * 添加或修改商品
     *
     * @param goods
     * @return
     */
    @RequestMapping("/save")
    public ModelAndView save(Goods goods) {
        ModelAndView mav = new ModelAndView();
        if (goods.getId() == null) {
            goods.setAddTime(new Date());
            goods.setState(0);
            goods.setIsRecommend(0);
            goods.setClick(0);
            goodsService.add(goods);
        } else {
            goods.setClick(goods.getClick() + 1);
            goodsService.update(goods);
        }
        mav.addObject("title", "我的商品--LeDao校园二手交易平台");
        mav.addObject("mainPage", "page/goodsManage");
        mav.addObject("mainPageKey", "#b");
        mav.setViewName("index");
        return mav;
    }
}
