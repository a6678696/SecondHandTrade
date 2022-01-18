package com.ledao.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.gson.Gson;
import com.ledao.entity.*;
import com.ledao.entity.Goods;
import com.ledao.service.*;
import com.ledao.util.RedisUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.*;

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

    @Value("${wantToBuyId}")
    private String wantToBuyId;

    @Resource
    private GoodsService goodsService;

    @Resource
    private GoodsTypeService goodsTypeService;

    @Resource
    private UserService userService;

    @Resource
    private ContactInformationService contactInformationService;

    @Resource
    private ReserveRecordService reserveRecordService;

    @Resource
    private MessageService messageService;

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
        getFirstImageInGoodsContent(goods);
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
        Collections.shuffle(goodsRecommendList);
        mav.addObject("goodsRecommendList", goodsRecommendList);
        //获取卖家邮箱
        String emailStr = userService.findById(goods.getUserId()).getEmail();
        mav.addObject("emailStr", emailStr);
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
        ModelAndView mav = new ModelAndView("redirect:/toGoodsManagePage");
        if (goods.getId() == null) {
            goods.setAddTime(new Date());
            goods.setState(0);
            goods.setIsRecommend(0);
            goods.setClick(0);
            goodsService.add(goods);
        } else {
            goods.setState(0);
            goodsService.update(goods);
        }
        return mav;
    }

    /**
     * 根据商品名称搜索商品
     *
     * @param name
     * @return
     */
    @RequestMapping("/search")
    public ModelAndView search(String name, Integer goodsType) {
        ModelAndView mav = new ModelAndView();
        QueryWrapper<Goods> goodsQueryWrapper = new QueryWrapper<>();
        goodsQueryWrapper.like("name", name);
        goodsQueryWrapper.orderByDesc("click");
        if (goodsType == 1) {
            goodsQueryWrapper.ne("goodsTypeId", wantToBuyId);
        } else if (goodsType == 2) {
            goodsQueryWrapper.eq("goodsTypeId", wantToBuyId);
            goodsQueryWrapper.eq("state", 1);
        }
        List<Goods> goodsList = goodsService.list(goodsQueryWrapper);
        for (Goods goods : goodsList) {
            goods.setGoodsTypeName(goodsTypeService.findById(goods.getGoodsTypeId()).getName());
            getFirstImageInGoodsContent(goods);
        }
        mav.addObject("goodsList", goodsList);
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
        for (Goods goods : goodsRecommendList) {
            getFirstImageInGoodsContent(goods);
            goods.setGoodsTypeName(goodsTypeService.findById(goods.getGoodsTypeId()).getName());
        }
        Collections.shuffle(goodsRecommendList);
        mav.addObject("goodsRecommendList", goodsRecommendList);
        mav.addObject("name", name);
        mav.addObject("goodsType", goodsType);
        mav.addObject("title", "搜索(" + name + ")的结果--LeDao校园二手交易平台");
        mav.addObject("mainPage", "page/goodsSearchResult");
        mav.addObject("mainPageKey", "#b");
        mav.setViewName("index");
        return mav;
    }

    /**
     * 将商品加入购物车
     *
     * @param goodsId
     * @return
     */
    @ResponseBody
    @RequestMapping("/addGoodsToShoppingCart")
    public Map<String, Object> addGoodsToShoppingCart(Integer goodsId, HttpSession session) {
        Map<String, Object> resultMap = new HashMap<>(16);
        Gson gson = new Gson();
        Goods goods = goodsService.findById(goodsId);
        User currentUser = (User) session.getAttribute("currentUser");
        if (currentUser == null) {
            resultMap.put("success", false);
            resultMap.put("errorInfo", "你的登录状态已经过期，请重新登录！！");
            return resultMap;
        }
        String shoppingCartName = currentUser.getId() + "_shoppingCart";
        List<String> shoppingCartGoodsStr = RedisUtil.listRange(shoppingCartName, 0L, -1L);
        for (int i = 0; i < shoppingCartGoodsStr.size(); i++) {
            Goods goods1 = gson.fromJson(shoppingCartGoodsStr.get(i), Goods.class);
            if (goods.getId().equals(goods1.getId())) {
                resultMap.put("success", false);
                resultMap.put("errorInfo", "加入购物车失败，这个商品已经在你的购物车里了哦！！");
                return resultMap;
            }
        }
        boolean key = RedisUtil.listRightPush(shoppingCartName, gson.toJson(goods));
        if (key) {
            resultMap.put("success", true);
        } else {
            resultMap.put("success", false);
        }
        return resultMap;
    }

    /**
     * 将商品从购物车删除
     *
     * @param goodsId
     * @return
     */
    @ResponseBody
    @RequestMapping("/deleteGoodsInShoppingCart")
    public Map<String, Object> deleteGoodsInShoppingCart(Integer goodsId, HttpSession session) {
        Map<String, Object> resultMap = new HashMap<>(16);
        Gson gson = new Gson();
        Goods goods = goodsService.findById(goodsId);
        User currentUser = (User) session.getAttribute("currentUser");
        if (currentUser == null) {
            resultMap.put("success", false);
            resultMap.put("errorInfo", "你的登录状态已经过期，请重新登录！！");
            return resultMap;
        }
        String shoppingCartName = currentUser.getId() + "_shoppingCart";
        List<String> shoppingCartGoodsStr = RedisUtil.listRange(shoppingCartName, 0L, -1L);
        boolean deleteKey = false;
        for (int i = 0; i < shoppingCartGoodsStr.size(); i++) {
            Goods goods1 = gson.fromJson(shoppingCartGoodsStr.get(i), Goods.class);
            if (goods.getId().equals(goods1.getId())) {
                shoppingCartGoodsStr.remove(shoppingCartGoodsStr.get(i));
                i--;
                deleteKey = true;
                RedisUtil.delKey(shoppingCartName);
                break;
            }
        }
        if (shoppingCartGoodsStr.size() > 0) {
            for (String s : shoppingCartGoodsStr) {
                RedisUtil.listRightPush(shoppingCartName, s);
            }
        }
        if (deleteKey) {
            resultMap.put("success", true);
        } else {
            resultMap.put("success", false);
        }
        return resultMap;
    }

    /**
     * 修改商品状态
     *
     * @param goodsId
     * @param state
     * @return
     */
    @ResponseBody
    @RequestMapping("/updateGoodsState")
    public Map<String, Object> updateGoodsState(Integer goodsId, Integer state) {
        Map<String, Object> resultMap = new HashMap<>(16);
        Goods goods = goodsService.findById(goodsId);
        if (goods.getState() == 4) {
            ReserveRecord reserveRecord = reserveRecordService.findByGoodsIdAndState(goodsId, 0);
            reserveRecord.setState(1);
            reserveRecordService.update(reserveRecord);
            //卖家取消买家的预订，系统给买家发送一条消息
            Message message = new Message();
            message.setUserId(userService.findById(reserveRecord.getUserId()).getId());
            message.setContent("你预订的商品（" + goods.getName() + "）已被卖家取消预订！！");
            message.setTime(new Date());
            message.setIsRead(0);
            messageService.add(message);
        }
        goods.setState(state);
        int key = goodsService.update(goods);
        if (key > 0) {
            resultMap.put("success", true);
        } else {
            resultMap.put("success", false);
        }
        return resultMap;
    }

    /**
     * 删除商品
     *
     * @param goodsId
     * @return
     */
    @ResponseBody
    @RequestMapping("/delete")
    public Map<String, Object> delete(Integer goodsId) {
        Map<String, Object> resultMap = new HashMap<>(16);
        int key = goodsService.deleteById(goodsId);
        if (key > 0) {
            resultMap.put("success", true);
        } else {
            resultMap.put("success", false);
        }
        return resultMap;
    }

    @ResponseBody
    @RequestMapping("/findById")
    public Map<String, Object> findById(Integer goodsId) {
        Map<String, Object> resultMap = new HashMap<>(16);
        Goods goods = goodsService.findById(goodsId);
        int key = 0;
        if (goods != null) {
            key = 1;
        }
        if (key > 0) {
            resultMap.put("success", true);
            resultMap.put("goods", goods);
        } else {
            resultMap.put("success", false);
        }
        return resultMap;
    }
}
