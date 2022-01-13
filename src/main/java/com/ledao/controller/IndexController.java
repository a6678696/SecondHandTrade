package com.ledao.controller;

import com.ledao.entity.User;
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
     * 管理员登录
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
                if (currentUser.getType() == 1) {
                    //用户没有被封禁
                    if (currentUser.getStatus() == 1) {
                        //密码正确时
                        if (currentUser.getPassword().equals(user.getPassword())) {
                            resultMap.put("success", true);
                            resultMap.put("currentUserType", currentUser.getType());
                            session.setAttribute("currentUserAdmin", currentUser);
                        } else {
                            resultMap.put("success", false);
                            resultMap.put("errorInfo", "用户名或密码错误,请重新输入!!");
                        }
                    } else {
                        resultMap.put("success", false);
                        resultMap.put("errorInfo", "你的账号已被封禁，如要解禁联系管理员\n管理员邮箱为：1234567890@qq.com");
                    }
                } else {
                    resultMap.put("success", false);
                    resultMap.put("errorInfo", "请使用管理员身份登录!!");
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
        session.removeAttribute("currentUserAdmin");
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
        User currentUser = (User) session.getAttribute("currentUserAdmin");
        if (currentUser != null) {
            resultMap.put("success", true);
            resultMap.put("currentUserAdmin", currentUser);
        } else {
            resultMap.put("success", false);
        }
        return resultMap;
    }

    /**
     * 首页
     *
     * @return
     */
    @RequestMapping("/")
    public ModelAndView root() {
        ModelAndView mav = new ModelAndView();
        mav.addObject("title", "首页--LeDao校园二手交易平台");
        mav.addObject("mainPage", "page/indexFirst");
        mav.addObject("mainPageKey", "#b");
        mav.setViewName("index");
        return mav;
    }

    /**
     * 跳转到用户登录界面
     *
     * @return
     */
    @RequestMapping("/toLoginPage")
    public ModelAndView toLoginPage() {
        ModelAndView mav = new ModelAndView();
        mav.addObject("title", "用户登录--LeDao校园二手交易平台");
        mav.addObject("mainPage", "page/login");
        mav.addObject("mainPageKey", "#b");
        mav.setViewName("index");
        return mav;
    }

    /**
     * 跳转到用户注册界面
     *
     * @return
     */
    @RequestMapping("/toRegisterPage")
    public ModelAndView toRegisterPage() {
        ModelAndView mav = new ModelAndView();
        mav.addObject("title", "用户注册--LeDao校园二手交易平台");
        mav.addObject("mainPage", "page/register");
        mav.addObject("mainPageKey", "#b");
        mav.setViewName("index");
        return mav;
    }

    /**
     * 跳转到找回密码界面
     *
     * @return
     */
    @RequestMapping("/toResetPasswordPage")
    public ModelAndView toResetPasswordPage() {
        ModelAndView mav = new ModelAndView();
        mav.addObject("title", "找回密码--LeDao校园二手交易平台");
        mav.addObject("mainPage", "page/resetPassword");
        mav.addObject("mainPageKey", "#b");
        mav.setViewName("index");
        return mav;
    }

    /**
     * 跳转到联系我们界面
     *
     * @return
     */
    @RequestMapping("/toContactPage")
    public ModelAndView toContactPage() {
        ModelAndView mav = new ModelAndView();
        mav.addObject("title", "联系我们--LeDao校园二手交易平台");
        mav.addObject("mainPage", "page/contact");
        mav.addObject("mainPageKey", "#b");
        mav.setViewName("index");
        return mav;
    }

    /**
     * 跳转到个人中心界面
     *
     * @return
     */
    @RequestMapping("/toPersonalHubsPage")
    public ModelAndView toPersonalHubsPage() {
        ModelAndView mav = new ModelAndView();
        mav.addObject("title", "个人中心--LeDao校园二手交易平台");
        mav.addObject("mainPage", "page/personalHubs");
        mav.addObject("mainPageKey", "#b");
        mav.setViewName("index");
        return mav;
    }

    /**
     * 跳转到查看个人信息界面
     *
     * @return
     */
    @RequestMapping("/toPersonalInfoPage")
    public ModelAndView toPersonalInfoPage() {
        ModelAndView mav = new ModelAndView();
        mav.addObject("title", "个人中心--LeDao校园二手交易平台");
        mav.addObject("mainPage", "page/personalInfo");
        mav.addObject("mainPageKey", "#b");
        mav.setViewName("index");
        return mav;
    }

    /**
     * 跳转到发布商品界面
     *
     * @return
     */
    @RequestMapping("/toAddGoodsPage")
    public ModelAndView toAddGoodsPage() {
        ModelAndView mav = new ModelAndView();
        mav.addObject("title", "发布商品--LeDao校园二手交易平台");
        mav.addObject("mainPage", "page/addGoods");
        mav.addObject("mainPageKey", "#b");
        mav.setViewName("index");
        return mav;
    }

    /**
     * 跳转到我的商品管理界面
     *
     * @return
     */
    @RequestMapping("/toGoodsManagePage")
    public ModelAndView toGoodsManagePage() {
        ModelAndView mav = new ModelAndView();
        mav.addObject("title", "我的商品管理--LeDao校园二手交易平台");
        mav.addObject("mainPage", "page/goodsManage");
        mav.addObject("mainPageKey", "#b");
        mav.setViewName("index");
        return mav;
    }

    /**
     * 跳转到我的消息界面
     *
     * @return
     */
    @RequestMapping("/toMyMessagePage")
    public ModelAndView toMyMessagePage() {
        ModelAndView mav = new ModelAndView();
        mav.addObject("title", "我的消息--LeDao校园二手交易平台");
        mav.addObject("mainPage", "page/myMessage");
        mav.addObject("mainPageKey", "#b");
        mav.setViewName("index");
        return mav;
    }
}
