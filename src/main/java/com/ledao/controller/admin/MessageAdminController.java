package com.ledao.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ledao.entity.Goods;
import com.ledao.entity.Message;
import com.ledao.entity.User;
import com.ledao.service.MessageService;
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
 * 后台消息Controller层
 *
 * @author LeDao
 * @company
 * @create 2022-01-18 14:30
 */
@RestController
@RequestMapping("/admin/message")
public class MessageAdminController {

    @Resource
    private MessageService messageService;

    @Resource
    private UserService userService;

    /**
     * 分页条件查询消息
     *
     * @param message
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping("/list")
    public Map<String, Object> list(Message message, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "rows", required = false) Integer rows) {
        Map<String, Object> resultMap = new HashMap<>(16);
        QueryWrapper<Message> messageQueryWrapper = new QueryWrapper<>();
        Page<Message> messagePage = new Page<>(page, rows);
        if (message.getUserName() != null) {
            QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
            userQueryWrapper.like("userName", message.getUserName());
            List<Integer> userIdList = new ArrayList<>();
            List<User> userList = userService.list(userQueryWrapper);
            if (userList.size() > 0) {
                for (User user : userList) {
                    userIdList.add(user.getId());
                }
            } else {
                userIdList.add(-1);
            }
            messageQueryWrapper.in("userId", userIdList);
        }
        messageQueryWrapper.orderByDesc("time");
        List<Message> messageList = messageService.list(messagePage, messageQueryWrapper);
        for (Message message1 : messageList) {
            message1.setUserName(userService.findById(message1.getUserId()).getUserName());
        }
        Integer total = messageService.getCount(messageQueryWrapper);
        resultMap.put("rows", messageList);
        resultMap.put("total", total);
        return resultMap;
    }

    /**
     * 删除消息,可批量删除
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
            messageService.deleteById(id);
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
