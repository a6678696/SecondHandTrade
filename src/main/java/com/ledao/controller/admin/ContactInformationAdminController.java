package com.ledao.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ledao.entity.ContactInformation;
import com.ledao.entity.User;
import com.ledao.service.ContactInformationService;
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
 * 后台联系方式Controller层
 *
 * @author LeDao
 * @company
 * @create 2022-01-13 0:11
 */
@RestController
@RequestMapping("/admin/contactInformation")
public class ContactInformationAdminController {

    @Resource
    private ContactInformationService contactInformationService;

    @Resource
    private UserService userService;

    /**
     * 分页条件查询联系方式
     *
     * @param contactInformation
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping("/list")
    public Map<String, Object> list(ContactInformation contactInformation, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "rows", required = false) Integer rows) {
        Map<String, Object> resultMap = new HashMap<>(16);
        QueryWrapper<ContactInformation> contactInformationQueryWrapper = new QueryWrapper<>();
        if (contactInformation.getName() != null) {
            contactInformationQueryWrapper.like("name", contactInformation.getName());
        }
        if (contactInformation.getUserName() != null) {
            List<String> userIdList = new ArrayList<>();
            QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
            userQueryWrapper.like("userName", contactInformation.getUserName());
            List<User> userList = userService.list(userQueryWrapper);
            for (User user : userList) {
                userIdList.add(String.valueOf(user.getId()));
            }
            contactInformationQueryWrapper.in("userId", userIdList);
        }
        Page<ContactInformation> contactInformationPage = new Page<>(page, rows);
        List<ContactInformation> contactInformationList = contactInformationService.list(contactInformationQueryWrapper, contactInformationPage);
        for (ContactInformation information : contactInformationList) {
            User user = userService.findById(information.getUserId());
            information.setUser(user);
        }
        Integer total = contactInformationService.getCount(contactInformationQueryWrapper);
        resultMap.put("rows", contactInformationList);
        resultMap.put("total", total);
        return resultMap;
    }

    /**
     * 添加或修改联系方式
     *
     * @param contactInformation
     * @return
     */
    @RequestMapping("/save")
    public Map<String, Object> save(ContactInformation contactInformation) {
        Map<String, Object> resultMap = new HashMap<>(16);
        int result;
        //id存在,修改联系方式
        if (contactInformation.getId() != null) {
            result = contactInformationService.update(contactInformation);
        } else {//id不存在,添加联系方式
            result = contactInformationService.add(contactInformation);
        }
        if (result > 0) {
            resultMap.put("success", true);
        } else {
            resultMap.put("success", false);
        }
        return resultMap;
    }

    /**
     * 删除联系方式，可批量删除
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
            contactInformationService.deleteById(Integer.parseInt(idsStr[i]));
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
