package com.ledao.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ledao.entity.PageBean;
import com.ledao.entity.User;
import com.ledao.service.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author LeDao
 * @company
 * @create 2022-01-04 12:13
 */
@RestController
@RequestMapping("/admin/user")
public class UserAdminController {

    @Resource
    private UserService userService;

    /**
     * 分页条件查询用户
     *
     * @param user
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping("/list")
    public Map<String, Object> list(User user, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "rows", required = false) Integer rows) {
        Map<String, Object> resultMap = new HashMap<>(16);
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        if (user.getUserName() != null) {
            userQueryWrapper.like("userName", user.getUserName());
        }
        userQueryWrapper.eq("type", 2);
        Page<User> userPage = new Page<>(page, rows);
        List<User> userList = userService.list(userQueryWrapper, userPage);
        Integer total = userService.getCount(userQueryWrapper);
        resultMap.put("rows", userList);
        resultMap.put("total", total);
        return resultMap;
    }

    /**
     * 添加或修改用户
     *
     * @param user
     * @return
     */
    @RequestMapping("/save")
    public Map<String, Object> save(User user) {
        Map<String, Object> resultMap = new HashMap<>(16);
        int result;
        //id存在,修改用户
        if (user.getId() != null) {
            user.setType(2);
            user.setStatus(1);
            result = userService.update(user);
        } else {//id不存在,添加用户
            user.setType(2);
            user.setStatus(1);
            result = userService.add(user);
        }
        if (result > 0) {
            resultMap.put("success", true);
        } else {
            resultMap.put("success", false);
        }
        return resultMap;
    }

    /**
     * 封禁或解禁用户
     *
     * @param id
     * @param status
     * @return
     */
    @RequestMapping("/banUser")
    public Map<String, Object> banUser(Integer id, Integer status) {
        Map<String, Object> resultMap = new HashMap<>(16);
        User user = userService.findById(id);
        user.setStatus(status);
        int key = userService.update(user);
        if (key > 0) {
            resultMap.put("success", true);
        } else {
            resultMap.put("success", false);
        }
        return resultMap;
    }

    /**
     * 删除用户，可批量删除
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
            userService.delete(Integer.parseInt(idsStr[i]));
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
