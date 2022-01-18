package com.ledao.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ledao.entity.Goods;
import com.ledao.entity.PayRecord;
import com.ledao.entity.User;
import com.ledao.service.GoodsService;
import com.ledao.service.PayRecordService;
import com.ledao.service.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 后台支付记录Controller层
 *
 * @author LeDao
 * @company
 * @create 2022-01-18 1:11
 */
@RestController
@RequestMapping("/admin/payRecord")
public class PayRecordAdminController {

    @Resource
    private PayRecordService payRecordService;

    @Resource
    private UserService userService;

    @Resource
    private GoodsService goodsService;

    /**
     * 分页条件查询支付记录
     *
     * @param payRecord
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping("/list")
    public Map<String, Object> list(PayRecord payRecord, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "rows", required = false) Integer rows) {
        Map<String, Object> resultMap = new HashMap<>(16);
        QueryWrapper<PayRecord> payRecordQueryWrapper = new QueryWrapper<>();
        Page<PayRecord> payRecordPage = new Page<>(page, rows);
        if (payRecord.getUserName() != null) {
            QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
            userQueryWrapper.like("userName", payRecord.getUserName());
            List<Integer> userIdList = new ArrayList<>();
            List<User> userList = userService.list(userQueryWrapper);
            if (userList.size() > 0) {
                for (User user : userList) {
                    userIdList.add(user.getId());
                }
            } else {
                userIdList.add(-1);
            }
            payRecordQueryWrapper.in("userId", userIdList);
        }
        List<PayRecord> payRecordList = payRecordService.list(payRecordPage, payRecordQueryWrapper);
        for (PayRecord record : payRecordList) {
            record.setGoodsName(goodsService.findById(record.getGoodsId()).getName());
            record.setUserName(userService.findById(record.getUserId()).getUserName());
        }
        Integer total = payRecordService.getCount(payRecordQueryWrapper);
        resultMap.put("rows", payRecordList);
        resultMap.put("total", total);
        return resultMap;
    }

    /**
     * 删除支付记录,可批量删除
     *
     * @param ids
     * @return
     */
    @RequestMapping("/delete")
    public Map<String, Object> delete(String ids) {
        Map<String, Object> resultMap = new HashMap<>(16);
        String[] idsStr = ids.split(",");
        int key = 0;
        for (int i = 0; i < idsStr.length; i++) {
            Integer id = Integer.valueOf(idsStr[i]);
            PayRecord payRecord = payRecordService.findById(id);
            //删除前将对应商品设置为上架状态
            Goods goods = goodsService.findById(payRecord.getGoodsId());
            goods.setState(1);
            goodsService.update(goods);
            payRecordService.deleteById(id);
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
