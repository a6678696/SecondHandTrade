package com.ledao.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ledao.entity.Goods;
import com.ledao.entity.ReserveRecord;
import com.ledao.entity.User;
import com.ledao.service.GoodsService;
import com.ledao.service.ReserveRecordService;
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
 * 后台预订记录Controller层
 *
 * @author LeDao
 * @company
 * @create 2022-01-18 1:11
 */
@RestController
@RequestMapping("/admin/reserveRecord")
public class ReserveRecordAdminController {

    @Resource
    private ReserveRecordService reserveRecordService;

    @Resource
    private UserService userService;

    @Resource
    private GoodsService goodsService;

    /**
     * 分页条件查询预订记录
     *
     * @param reserveRecord
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping("/list")
    public Map<String, Object> list(ReserveRecord reserveRecord, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "rows", required = false) Integer rows) {
        Map<String, Object> resultMap = new HashMap<>(16);
        QueryWrapper<ReserveRecord> reserveRecordQueryWrapper = new QueryWrapper<>();
        Page<ReserveRecord> reserveRecordPage = new Page<>(page, rows);
        if (reserveRecord.getUserName() != null) {
            QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
            userQueryWrapper.like("userName", reserveRecord.getUserName());
            List<Integer> userIdList = new ArrayList<>();
            List<User> userList = userService.list(userQueryWrapper);
            if (userList.size() > 0) {
                for (User user : userList) {
                    userIdList.add(user.getId());
                }
            } else {
                userIdList.add(-1);
            }
            reserveRecordQueryWrapper.in("userId", userIdList);
        }
        reserveRecordQueryWrapper.orderByDesc("reserveTime");
        List<ReserveRecord> reserveRecordList = reserveRecordService.list(reserveRecordPage, reserveRecordQueryWrapper);
        for (ReserveRecord record : reserveRecordList) {
            record.setGoodsName(goodsService.findById(record.getGoodsId()).getName());
            record.setUserName(userService.findById(record.getUserId()).getUserName());
        }
        Integer total = reserveRecordService.getCount(reserveRecordQueryWrapper);
        resultMap.put("rows", reserveRecordList);
        resultMap.put("total", total);
        return resultMap;
    }

    /**
     * 删除预订记录,可批量删除
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
            ReserveRecord reserveRecord = reserveRecordService.findById(id);
            //删除前将对应商品设置为上架状态
            Goods goods = goodsService.findById(reserveRecord.getGoodsId());
            goods.setState(1);
            goodsService.update(goods);
            reserveRecordService.deleteById(id);
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
