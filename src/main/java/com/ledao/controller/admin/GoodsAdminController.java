package com.ledao.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ledao.entity.Goods;
import com.ledao.entity.User;
import com.ledao.service.GoodsService;
import com.ledao.service.GoodsTypeService;
import com.ledao.service.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.*;

/**
 * 后台商品Controller层
 *
 * @author LeDao
 * @company
 * @create 2022-01-18 15:17
 */
@RestController
@RequestMapping("/admin/goods")
public class GoodsAdminController {

    @Resource
    private GoodsService goodsService;

    @Resource
    private GoodsTypeService goodsTypeService;

    @Resource
    private UserService userService;

    /**
     * 分页条件查询商品
     *
     * @param goods
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping("/list")
    public Map<String, Object> list(Goods goods, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "rows", required = false) Integer rows) {
        Map<String, Object> resultMap = new HashMap<>(16);
        QueryWrapper<Goods> goodsQueryWrapper = new QueryWrapper<>();
        if (goods.getName() != null) {
            goodsQueryWrapper.like("name", goods.getName());
        }
        if (goods.getState() != null) {
            goodsQueryWrapper.eq("state", goods.getState());
        }
        if (goods.getIsRecommend() != null) {
            goodsQueryWrapper.eq("isRecommend", goods.getIsRecommend());
        }
        if (goods.getGoodsTypeId() != null) {
            goodsQueryWrapper.eq("goodsTypeId", goods.getGoodsTypeId());
        }
        if (goods.getUserName() != null) {
            List<Integer> userIdList = new ArrayList<>();
            QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
            userQueryWrapper.like("userName", goods.getUserName());
            List<User> userList = userService.list(userQueryWrapper);
            if (userList.size() > 0) {
                for (User user : userList) {
                    userIdList.add(user.getId());
                }
            } else {
                userIdList.add(-1);
            }
            goodsQueryWrapper.in("userId", userIdList);
        }
        goodsQueryWrapper.orderByDesc("addTime");
        Page<Goods> goodsPage = new Page<>(page, rows);
        List<Goods> goodsList = goodsService.list(goodsPage, goodsQueryWrapper);
        for (Goods goods1 : goodsList) {
            goods1.setUserName(userService.findById(goods1.getUserId()).getUserName());
            goods1.setGoodsTypeName(goodsTypeService.findById(goods1.getGoodsTypeId()).getName());
        }
        Integer total = goodsService.getCount(goodsQueryWrapper);
        resultMap.put("rows", goodsList);
        resultMap.put("total", total);
        return resultMap;
    }

    /**
     * 推荐或不推荐商品
     *
     * @param id
     * @param isRecommend
     * @return
     */
    @RequestMapping("/recommendGoodsOrNot")
    public Map<String, Object> recommendGoodsOrNot(Integer id, Integer isRecommend,Integer recommendDays) {
        Map<String, Object> resultMap = new HashMap<>(16);
        Goods goods = goodsService.findById(id);
        goods.setIsRecommend(isRecommend);
        if (isRecommend == 1) {
            goods.setRecommendTime(new Date());
            goods.setRecommendDays(recommendDays);
        }
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
     * @param ids
     * @return
     */
    @RequestMapping("/delete")
    public Map<String, Object> delete(String ids) {
        Map<String, Object> resultMap = new HashMap<>(16);
        String[] idsStr = ids.split(",");
        int key = 0;
        for (String s : idsStr) {
            Integer id = Integer.valueOf(s);
            goodsService.deleteById(id);
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
