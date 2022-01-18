package com.ledao.controller;

import java.util.Date;

import com.ledao.entity.Goods;
import com.ledao.entity.Message;
import com.ledao.entity.ReserveRecord;
import com.ledao.entity.User;
import com.ledao.service.GoodsService;
import com.ledao.service.MessageService;
import com.ledao.service.ReserveRecordService;
import com.ledao.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * 前台预定记录Controller层
 *
 * @author LeDao
 * @company
 * @create 2022-01-17 3:42
 */
@Controller
@RequestMapping("/reserveRecord")
public class ReserveRecordController {

    @Resource
    private ReserveRecordService reserveRecordService;

    @Resource
    private GoodsService goodsService;

    @Resource
    private MessageService messageService;

    @Resource
    private UserService userService;

    /**
     * 给购物车的某一商品预定记录
     *
     * @param goodsId
     * @param session
     * @return
     */
    @ResponseBody
    @RequestMapping("/reserve")
    public Map<String, Object> reserve(Integer goodsId, HttpSession session) {
        Map<String, Object> resultMap = new HashMap<>(16);
        User currentUser = (User) session.getAttribute("currentUser");
        Goods goods = goodsService.findById(goodsId);
        goods.setState(4);
        goodsService.update(goods);
        ReserveRecord reserveRecord = new ReserveRecord();
        reserveRecord.setGoodsId(goodsId);
        reserveRecord.setUserId(currentUser.getId());
        reserveRecord.setReserveTime(new Date());
        reserveRecord.setIsCancel(0);
        int key = reserveRecordService.add(reserveRecord);
        Message message = new Message();
        message.setUserId(userService.findById(goods.getUserId()).getId());
        message.setContent("你的商品（" + goods.getName() + "）已被预定，请联系买家当面交易哦！！");
        message.setTime(new Date());
        message.setIsRead(0);
        messageService.add(message);
        if (key > 0) {
            resultMap.put("success", true);
        } else {
            resultMap.put("success", false);
        }
        return resultMap;
    }
}
