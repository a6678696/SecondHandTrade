package com.ledao.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ledao.entity.Contact;
import com.ledao.entity.User;
import com.ledao.service.ContactService;
import com.ledao.service.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 后台留言Controller层
 *
 * @author LeDao
 * @company
 * @create 2022-01-13 1:49
 */
@RestController
@RequestMapping("/admin/contact")
public class ContactAdminController {

    @Resource
    private ContactService contactService;

    @Resource
    private UserService userService;

    /**
     * 分页条件查询留言
     *
     * @param contact
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping("/list")
    public Map<String, Object> list(Contact contact, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "rows", required = false) Integer rows) {
        Map<String, Object> resultMap = new HashMap<>(16);
        QueryWrapper<Contact> contactQueryWrapper = new QueryWrapper<>();
        contactQueryWrapper.orderByDesc("time");
        if (contact.getUserName() != null) {
            List<String> userIdList = new ArrayList<>();
            QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
            userQueryWrapper.like("userName", contact.getUserName());
            List<User> userList = userService.list(userQueryWrapper);
            for (User user : userList) {
                userIdList.add(String.valueOf(user.getId()));
            }
            contactQueryWrapper.in("userId", userIdList);
        }
        Page<Contact> contactPage = new Page<>(page, rows);
        List<Contact> contactList = contactService.list(contactQueryWrapper, contactPage);
        for (Contact contact1 : contactList) {
            contact1.setUserName(userService.findById(contact1.getUserId()).getUserName());
        }
        Integer total = contactService.getCount(contactQueryWrapper);
        resultMap.put("rows", contactList);
        resultMap.put("total", total);
        return resultMap;
    }

    /**
     * 添加或修改留言
     *
     * @param contact
     * @return
     */
    @RequestMapping("/save")
    public Map<String, Object> save(Contact contact, HttpSession session) {
        Map<String, Object> resultMap = new HashMap<>(16);
        int result;
        //id存在,修改留言
        if (contact.getId() != null) {
            User currentUserAdmin = (User) session.getAttribute("currentUserAdmin");
            contact.setUserIdReply(currentUserAdmin.getId());
            result = contactService.update(contact);
        } else {//id不存在,添加留言
            result = contactService.add(contact);
        }
        if (result > 0) {
            resultMap.put("success", true);
        } else {
            resultMap.put("success", false);
        }
        return resultMap;
    }

    /**
     * 删除留言，可批量删除
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
            contactService.delete(Integer.parseInt(idsStr[i]));
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
