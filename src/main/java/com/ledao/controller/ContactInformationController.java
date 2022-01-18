package com.ledao.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ledao.entity.ContactInformation;
import com.ledao.entity.User;
import com.ledao.service.ContactInformationService;
import com.ledao.service.GoodsService;
import com.ledao.service.ReserveRecordService;
import com.ledao.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.naming.Name;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 联系方式Controller层
 *
 * @author LeDao
 * @company
 * @create 2022-01-12 17:21
 */
@Controller
@RequestMapping("/contactInformation")
public class ContactInformationController {

    @Resource
    private ContactInformationService contactInformationService;

    @Resource
    private GoodsService goodsService;

    @Resource
    private UserService userService;

    @Resource
    private ReserveRecordService reserveRecordService;

    /**
     * 添加或修改联系方式
     *
     * @param contactInformation
     * @return
     */
    @RequestMapping("/save")
    public ModelAndView save(ContactInformation contactInformation, HttpSession session) {
        ModelAndView mav = new ModelAndView();
        User currentUser = (User) session.getAttribute("currentUser");
        if (contactInformation.getId() == null) {
            int key = contactInformationService.add(contactInformation);
            if (key > 0) {
                mav.addObject("addContactInformationSuccess", true);
            } else {
                mav.addObject("addContactInformationSuccess", false);
            }
        } else {
            int key = contactInformationService.update(contactInformation);
            if (key > 0) {
                mav.addObject("updateContactInformationSuccess", true);
            } else {
                mav.addObject("updateContactInformationSuccess", false);
            }
        }
        QueryWrapper<ContactInformation> contactInformationQueryWrapper = new QueryWrapper<>();
        contactInformationQueryWrapper.eq("userId", currentUser.getId());
        Page<ContactInformation> contactInformationPage = new Page<>(1, 100);
        List<ContactInformation> contactInformationList = contactInformationService.list(contactInformationQueryWrapper, contactInformationPage);
        mav.addObject("contactInformationList", contactInformationList);
        mav.addObject("title", "联系方式--LeDao校园二手交易平台");
        mav.addObject("mainPage", "page/myContactInformation");
        mav.addObject("mainPageKey", "#b");
        mav.setViewName("index");
        return mav;
    }

    /**
     * 跳转到我的联系方式界面
     *
     * @return
     */
    @RequestMapping("/toMyContactInformationPage")
    public ModelAndView toMyContactInformation(ContactInformation contactInformation, HttpSession session) {
        ModelAndView mav = new ModelAndView();
        User currentUser = (User) session.getAttribute("currentUser");
        QueryWrapper<ContactInformation> contactInformationQueryWrapper = new QueryWrapper<>();
        contactInformationQueryWrapper.eq("userId", currentUser.getId());
        if (contactInformation.getName() != null) {
            contactInformationQueryWrapper.like("name", contactInformation.getName());
        }
        Page<ContactInformation> contactInformationPage = new Page<>(1, 100);
        List<ContactInformation> contactInformationList = contactInformationService.list(contactInformationQueryWrapper, contactInformationPage);
        mav.addObject("name", contactInformation.getName());
        mav.addObject("contactInformationList", contactInformationList);
        mav.addObject("title", "联系方式--LeDao校园二手交易平台");
        mav.addObject("mainPage", "page/myContactInformation");
        mav.addObject("mainPageKey", "#b");
        mav.setViewName("index");
        return mav;
    }

    @RequestMapping("/delete")
    public ModelAndView delete(Integer id, HttpSession session) {
        ModelAndView mav = new ModelAndView();
        User currentUser = (User) session.getAttribute("currentUser");
        int key = contactInformationService.deleteById(id);
        if (key > 0) {
            mav.addObject("deleteContactInformation", true);
        } else {
            mav.addObject("deleteContactInformation", false);
        }
        QueryWrapper<ContactInformation> contactInformationQueryWrapper = new QueryWrapper<>();
        contactInformationQueryWrapper.eq("userId", currentUser.getId());
        Page<ContactInformation> contactInformationPage = new Page<>(1, 100);
        List<ContactInformation> contactInformationList = contactInformationService.list(contactInformationQueryWrapper, contactInformationPage);
        mav.addObject("contactInformationList", contactInformationList);
        mav.addObject("title", "联系方式--LeDao校园二手交易平台");
        mav.addObject("mainPage", "page/myContactInformation");
        mav.addObject("mainPageKey", "#b");
        mav.setViewName("index");
        return mav;
    }

    /**
     * 查看用户联系方式的名称是否已经存在
     *
     * @param name
     * @param userId
     * @return
     */
    @ResponseBody
    @RequestMapping("/checkSaveContactInformationName")
    public Map<String, Object> checkSaveContactInformationName(String name, Integer userId) {
        Map<String, Object> resultMap = new HashMap<>(16);
        ContactInformation contactInformation = contactInformationService.findByNameAndUserId(name, userId);
        if (contactInformation != null) {
            resultMap.put("success", true);
            resultMap.put("name", name);
        } else {
            resultMap.put("success", false);
        }
        return resultMap;
    }

    /**
     * 根据id查找联系方式
     *
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping("/findById")
    public Map<String, Object> findById(Integer id) {
        Map<String, Object> resultMap = new HashMap<>(16);
        ContactInformation contactInformation = contactInformationService.findById(id);
        if (contactInformation != null) {
            resultMap.put("success", true);
            resultMap.put("contactInformation", contactInformation);
        }
        return resultMap;
    }

    /**
     * 根据商品id获取联系方式
     *
     * @param goodsId
     * @return
     */
    @ResponseBody
    @RequestMapping("/getListByGoodsId")
    public Map<String, Object> getListByGoodsId(Integer goodsId) {
        Map<String, Object> resultMap = new HashMap<>(16);
        QueryWrapper<ContactInformation> contactInformationQueryWrapper = new QueryWrapper<>();
        contactInformationQueryWrapper.eq("userId", reserveRecordService.findByGoodsId(goodsId).getUserId());
        List<ContactInformation> contactInformationList = contactInformationService.list(contactInformationQueryWrapper);
        StringBuilder contactInformationStr = new StringBuilder();
        if (contactInformationList.size() > 0) {
            for (ContactInformation contactInformation : contactInformationList) {
                contactInformationStr.append(contactInformation.getName()).append("：").append(contactInformation.getContent()).append("；");
            }
        } else {
            contactInformationStr.append("买家邮箱：").append(userService.findById(reserveRecordService.findByGoodsId(goodsId).getUserId()).getEmail());
        }
        resultMap.put("success", true);
        resultMap.put("contactInformationStr", contactInformationStr);
        return resultMap;
    }
}
