package com.ledao.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ledao.entity.*;
import com.ledao.service.*;
import com.ledao.util.DateUtil;
import com.ledao.util.ImageUtil;
import com.ledao.util.StringUtil;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.ledao.controller.IndexController.getFirstImageInGoodsContent;

/**
 * 前台用户Controller层
 *
 * @author LeDao
 * @company
 * @create 2022-01-09 14:00
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Resource
    private ConfigProperties configProperties;

    @Resource
    private JavaMailSender javaMailSender;

    @Resource
    private UserService userService;

    @Resource
    private CarouselService carouselService;

    @Resource
    private AnnouncementService announcementService;

    @Resource
    private GoodsTypeService goodsTypeService;

    @Resource
    private GoodsService goodsService;

    /**
     * 前台用户登录
     *
     * @param user
     * @param session
     * @return
     */
    @RequestMapping("/login")
    public ModelAndView login(User user, HttpSession session) {
        ModelAndView mav = new ModelAndView();
        User currentUser = userService.findByUserName(user.getUserName());
        //用户存在时
        if (currentUser != null) {
            //登录的用户身份不是普通用户而是管理员
            if (currentUser.getType() != 2) {
                mav.addObject("userNameLogin", user.getUserName());
                mav.addObject("passwordLogin", user.getPassword());
                mav.addObject("title", "用户登录--LeDao校园二手交易平台");
                mav.addObject("mainPage", "page/login");
                mav.addObject("isUserOrNot", false);
            }else {
                //密码正确时
                if (user.getPassword().equals(currentUser.getPassword())) {
                    session.setAttribute("currentUser", currentUser);
                    //获取轮播图list
                    QueryWrapper<Carousel> carouselQueryWrapper = new QueryWrapper<>();
                    carouselQueryWrapper.orderByAsc("sortNum");
                    List<Carousel> carouselList = carouselService.list(carouselQueryWrapper);
                    mav.addObject("carouselList", carouselList);
                    //获取公告list
                    QueryWrapper<Announcement> announcementQueryWrapper = new QueryWrapper<>();
                    announcementQueryWrapper.orderByAsc("sortNum");
                    List<Announcement> announcementList = announcementService.list(announcementQueryWrapper);
                    mav.addObject("announcementList", announcementList);
                    //获取9个最近发布的商品
                    QueryWrapper<Goods> goodsQueryWrapper = new QueryWrapper<>();
                    goodsQueryWrapper.orderByDesc("addTime");
                    goodsQueryWrapper.eq("state", 1);
                    goodsQueryWrapper.ne("goodsTypeId", configProperties.getWantToBuyId());
                    Page<Goods> goodsPage = new Page<>(1, 9);
                    List<Goods> goodsNewList = goodsService.list(goodsPage, goodsQueryWrapper);
                    for (Goods goods : goodsNewList) {
                        getFirstImageInGoodsContent(goods);
                        goods.setGoodsTypeName(goodsTypeService.findById(goods.getGoodsTypeId()).getName());
                    }
                    mav.addObject("goodsNewList", goodsNewList);
                    //获取9个热门商品
                    QueryWrapper<Goods> goodsQueryWrapper2 = new QueryWrapper<>();
                    goodsQueryWrapper2.orderByDesc("click");
                    goodsQueryWrapper2.eq("state", 1);
                    goodsQueryWrapper2.ne("goodsTypeId", configProperties.getWantToBuyId());
                    Page<Goods> goodsPage2 = new Page<>(1, 9);
                    List<Goods> goodsHotList = goodsService.list(goodsPage2, goodsQueryWrapper2);
                    for (Goods goods : goodsHotList) {
                        getFirstImageInGoodsContent(goods);
                        goods.setGoodsTypeName(goodsTypeService.findById(goods.getGoodsTypeId()).getName());
                    }
                    mav.addObject("goodsHotList", goodsHotList);
                    //获取推荐商品列表
                    QueryWrapper<Goods> goodsQueryWrapper3 = new QueryWrapper<>();
                    goodsQueryWrapper3.eq("isRecommend", 1);
                    goodsQueryWrapper3.ne("goodsTypeId", configProperties.getWantToBuyId());
                    goodsQueryWrapper3.eq("state", 1);
                    Page<Goods> goodsPage3 = new Page<>(1, 9);
                    List<Goods> goodsRecommendList = goodsService.list(goodsPage3, goodsQueryWrapper3);
                    for (Goods goods : goodsRecommendList) {
                        getFirstImageInGoodsContent(goods);
                        goods.setGoodsTypeName(goodsTypeService.findById(goods.getGoodsTypeId()).getName());
                    }
                    Collections.shuffle(goodsRecommendList);
                    mav.addObject("goodsRecommendList", goodsRecommendList);
                    mav.addObject("title", "首页--LeDao校园二手交易平台");
                    mav.addObject("mainPage", "page/indexFirst");
                    mav.addObject("loginSuccess", true);
                } else {
                    mav.addObject("userNameLogin", user.getUserName());
                    mav.addObject("passwordLogin", user.getPassword());
                    mav.addObject("title", "用户登录--LeDao校园二手交易平台");
                    mav.addObject("mainPage", "page/login");
                    mav.addObject("loginSuccess", false);
                }
            }
        } else {
            mav.addObject("userNameLogin", user.getUserName());
            mav.addObject("passwordLogin", user.getPassword());
            mav.addObject("title", "用户登录--LeDao校园二手交易平台");
            mav.addObject("mainPage", "page/login");
            mav.addObject("loginSuccess", false);
        }
        mav.addObject("mainPageKey", "#b");
        mav.setViewName("index");
        return mav;
    }

    /**
     * 用户注销
     *
     * @param session
     * @param response
     * @throws IOException
     */
    @RequestMapping("/logout")
    public void logout(HttpSession session, HttpServletResponse response) throws IOException {
        session.removeAttribute("currentUser");
        response.sendRedirect("/toLoginPage");
    }

    /**
     * 添加或修改用户
     *
     * @param user
     * @param file
     * @param session
     * @return
     * @throws Exception
     */
    @RequestMapping("/save")
    public ModelAndView save(User user, @RequestParam("userImage") MultipartFile file, HttpSession session) throws Exception {
        //上传的图片存在时
        if (!file.isEmpty()) {
            //修改用户时,删除原头像
            if (user.getId() != null) {
                FileUtils.deleteQuietly(new File(configProperties.getUserImageFilePath() + userService.findById(user.getId()).getImageName()));
            }
            //获取上传的文件名
            String fileName = file.getOriginalFilename();
            //获取文件的后缀
            String suffixName = null;
            if (fileName != null) {
                suffixName = fileName.split("\\.")[1];
            }
            //新文件名1
            String newFileName1 = DateUtil.getCurrentDateStr2() + System.currentTimeMillis() + "." + suffixName;
            //上传
            FileUtils.copyInputStreamToFile(file.getInputStream(), new File(configProperties.getUserImageFilePath() + newFileName1));
            //新文件名2
            String newFileName2 = DateUtil.getCurrentDateStr2() + System.currentTimeMillis() + "." + suffixName;
            //压缩图片
            ImageUtil.compressImage(new File(configProperties.getUserImageFilePath() + newFileName1), new File(configProperties.getUserImageFilePath() + newFileName2));
            user.setImageName(newFileName2);
        }
        //添加用户时
        if (user.getId() == null) {
            user.setStatus(1);
            user.setType(2);
            userService.add(user);
            ModelAndView mav = new ModelAndView();
            mav.addObject("successRegister", true);
            mav.addObject("title", "用户登录");
            mav.addObject("mainPage", "page/login");
            mav.addObject("mainPageKey", "#b");
            mav.setViewName("index");
            return mav;
        } else {
            User trueUser = userService.findById(user.getId());
            trueUser.setPassword(user.getPassword());
            trueUser.setNickName(user.getNickName());
            if (!file.isEmpty()) {
                trueUser.setImageName(user.getImageName());
            }
            userService.update(trueUser);
            ModelAndView mav = new ModelAndView();
            session.setAttribute("currentUser", trueUser);
            mav.addObject("title", "个人中心--LeDao校园二手交易平台");
            mav.addObject("mainPage", "page/personalInfo");
            mav.addObject("mainPageKey", "#b");
            mav.addObject("modifyUserSuccess", true);
            mav.setViewName("index");
            return mav;
        }
    }

    /**
     * 用户注册时判断用户名是否已经存在
     *
     * @param userName
     * @return
     */
    @ResponseBody
    @RequestMapping("/existUserWithUserName")
    public Map<String, Object> existUserWithUserName(String userName) {
        Map<String, Object> resultMap = new HashMap<>(16);
        User user = userService.findByUserName(userName);
        if (user != null) {
            resultMap.put("success", true);
        } else {
            resultMap.put("success", false);
        }
        return resultMap;
    }

    /**
     * 判断数据库中邮箱是否存在
     *
     * @param email
     * @return
     */
    @ResponseBody
    @RequestMapping("/existEmail")
    public Map<String, Object> existEmail(String email) {
        Map<String, Object> resultMap = new HashMap<>(16);
        User user = userService.findByEmail(email);
        if (user != null) {
            resultMap.put("success", true);
        } else {
            resultMap.put("success", false);
        }
        return resultMap;
    }

    /**
     * 注册或找回密码时获取验证码
     *
     * @param session
     * @param email
     * @param type
     * @return
     */
    @ResponseBody
    @RequestMapping("/getVerificationCode")
    public Map<String, Object> getVerificationCode(HttpSession session, String email, Integer type) {
        Map<String, Object> resultMap = new HashMap<>(16);
        //生成6位数字验证码
        String registerCode = StringUtil.genSixRandomNum();
        SimpleMailMessage message = new SimpleMailMessage();
        //发件人QQ邮箱
        message.setFrom(configProperties.getSendMailPerson());
        //收件人邮箱
        message.setTo(email);
        //邮件主题
        message.setSubject("来自LeDao校园二手交易平台的邮件");
        //1为注册,2为找回密码
        int codeTypeRegister = 1, codeTypeResetPassword = 2;
        if (type == codeTypeRegister) {
            //邮件内容
            message.setText("注册的验证码为:" + registerCode);
            session.setAttribute("registerCode", registerCode);
        } else if (type == codeTypeResetPassword) {
            //邮件内容
            message.setText("找回密码的验证码为:" + registerCode);
            session.setAttribute("resetPasswordCode", registerCode);
        }
        //发送邮件
        javaMailSender.send(message);
        resultMap.put("success", true);
        return resultMap;
    }

    /**
     * 获取session中的注册验证码
     *
     * @param session
     * @return
     */
    @ResponseBody
    @RequestMapping("/getRegisterCode")
    public Map<String, Object> getRegisterCode(HttpSession session) {
        Map<String, Object> resultMap = new HashMap<>(16);
        String registerCode = (String) session.getAttribute("registerCode");
        System.out.println(registerCode);
        if (registerCode != null) {
            resultMap.put("imageCode", registerCode);
            resultMap.put("success", true);
        } else {
            resultMap.put("success", false);
        }
        return resultMap;
    }

    /**
     * 获取session中的找回密码验证码
     *
     * @param session
     * @return
     */
    @ResponseBody
    @RequestMapping("/getResetPasswordCode")
    public Map<String, Object> getResetPasswordCode(HttpSession session) {
        Map<String, Object> resultMap = new HashMap<>(16);
        String resetPasswordCode = (String) session.getAttribute("resetPasswordCode");
        System.out.println(resetPasswordCode);
        if (resetPasswordCode != null) {
            resultMap.put("imageCode", resetPasswordCode);
            resultMap.put("success", true);
        } else {
            resultMap.put("success", false);
        }
        return resultMap;
    }

    @ResponseBody
    @RequestMapping("/resetPassword")
    public ModelAndView resetPassword(User user) {
        ModelAndView mav = new ModelAndView();
        User resetPasswordUser = userService.findByEmail(user.getEmail());
        resetPasswordUser.setPassword(user.getPassword());
        userService.update(resetPasswordUser);
        mav.addObject("successResetPassword", true);
        mav.addObject("title", "用户登录");
        mav.addObject("mainPage", "page/login");
        mav.addObject("mainPageKey", "#b");
        mav.setViewName("index");
        return mav;
    }

    /**
     * 登录前检验用户的状态
     *
     * @param userName
     * @return
     */
    @ResponseBody
    @RequestMapping("/checkLoginUserState")
    public Map<String, Object> checkLoginUserState(String userName) {
        Map<String, Object> resultMap = new HashMap<>(16);
        User user = userService.findByUserName(userName);
        if (user != null) {
            resultMap.put("success", true);
            resultMap.put("status", user.getStatus());
        } else {
            resultMap.put("success", false);
        }
        return resultMap;
    }
}
