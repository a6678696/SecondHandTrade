package com.ledao.controller;

import java.util.Date;

import com.ledao.entity.Goods;
import com.ledao.entity.PayRecord;
import com.ledao.entity.User;
import com.ledao.service.GoodsService;
import com.ledao.service.PayRecordService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * 支付记录Controller层
 *
 * @author LeDao
 * @company
 * @create 2022-01-17 3:42
 */
@Controller
@RequestMapping("/payRecord")
public class PayRecordController {

    @Resource
    private PayRecordService payRecordService;

    @Resource
    private GoodsService goodsService;

    /**
     * 给购物车的某一商品支付
     *
     * @param goodsId
     * @param session
     * @return
     */
    @ResponseBody
    @RequestMapping("/pay")
    public Map<String, Object> pay(Integer goodsId, HttpSession session) {
        Map<String, Object> resultMap = new HashMap<>(16);
        User currentUser = (User) session.getAttribute("currentUser");
        Goods goods = goodsService.findById(goodsId);
        goods.setState(4);
        int key = goodsService.update(goods);
        PayRecord payRecord = new PayRecord();
        payRecord.setGoodsId(goodsId);
        payRecord.setUserId(currentUser.getId());
        payRecord.setPayTime(new Date());
        payRecordService.add(payRecord);
        if (key > 0) {
            resultMap.put("success", true);
        } else {
            resultMap.put("success", false);
        }
        return resultMap;
    }
}
