package com.ledao.controller;

import com.ledao.entity.User;
import com.ledao.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * 首页Controller
 *
 * @author LeDao
 * @company
 * @create 2022-01-03 14:03
 */
@Controller
public class IndexController {

    @Resource
    private UserService userService;

    /**
     * 用户登录
     *
     * @param user
     * @param session
     * @return
     */
    @ResponseBody
    @RequestMapping("/login")
    public Map<String, Object> login(User user, HttpSession session) {
        Map<String, Object> resultMap = new HashMap<>(16);
        String checkCode = (String) session.getAttribute("checkCode");
        //验证码正确
        if (checkCode.equals(user.getImageCode())) {
            User currentUser = userService.findByUserName(user.getUserName());
            //用户存在时
            if (currentUser != null) {
                //密码正确时
                if (currentUser.getPassword().equals(user.getPassword())) {
                    resultMap.put("success", true);
                    resultMap.put("currentUserType", currentUser.getType());
                    session.setAttribute("currentUser", currentUser);
                } else {
                    resultMap.put("success", false);
                    resultMap.put("errorInfo", "用户名或密码错误,请重新输入!!");
                }
            } else {
                resultMap.put("success", false);
                resultMap.put("errorInfo", "用户名或密码错误,请重新输入!!");
            }
        } else {
            resultMap.put("success", false);
            resultMap.put("errorInfo", "验证码错误,请重新输入!!");
        }
        return resultMap;
    }

    /**
     * 注销登录
     *
     * @param session
     * @return
     */
    @RequestMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("currentUser");
        return "redirect:/login.html";
    }

    /**
     * 获取当前登录用户信息
     *
     * @param session
     * @return
     */
    @ResponseBody
    @RequestMapping("/getUserInfo")
    public Map<String, Object> getUserInfo(HttpSession session) {
        Map<String, Object> resultMap = new HashMap<>(16);
        User currentUser = (User) session.getAttribute("currentUser");
        if (currentUser != null) {
            resultMap.put("success", true);
            resultMap.put("currentUser", currentUser);
        } else {
            resultMap.put("success", false);
        }
        return resultMap;
    }
}
