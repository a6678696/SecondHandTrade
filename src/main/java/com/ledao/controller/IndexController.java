package com.ledao.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.gson.Gson;
import com.ledao.entity.*;
import com.ledao.service.*;
import com.ledao.util.DateUtil;
import com.ledao.util.ImageUtil;
import com.ledao.util.RedisUtil;
import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.*;

/**
 * 首页Controller
 *
 * @author LeDao
 * @company
 * @create 2022-01-03 14:03
 */
@Controller
public class IndexController {

    @Value("${articleImageFilePath}")
    private String articleImageFilePath;

    @Value("${wantToBuyId}")
    private String wantToBuyId;

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
        goodsQueryWrapper.ne("goodsTypeId", wantToBuyId);
        goodsQueryWrapper.eq("state", 1);
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
        goodsQueryWrapper2.ne("goodsTypeId", wantToBuyId);
        goodsQueryWrapper2.eq("state", 1);
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
        goodsQueryWrapper3.eq("state", 1);
        goodsQueryWrapper3.ne("goodsTypeId", wantToBuyId);
        Page<Goods> goodsPage3 = new Page<>(1, 9);
        List<Goods> goodsRecommendList = goodsService.list(goodsPage3, goodsQueryWrapper3);
        for (Goods goods : goodsRecommendList) {
            getFirstImageInGoodsContent(goods);
            goods.setGoodsTypeName(goodsTypeService.findById(goods.getGoodsTypeId()).getName());
        }
        Collections.shuffle(goodsRecommendList);
        mav.addObject("goodsRecommendList", goodsRecommendList);
        mav.addObject("isHome", true);
        mav.addObject("title", "首页--LeDao校园二手交易平台");
        mav.addObject("mainPage", "page/indexFirst");
        mav.addObject("mainPageKey", "#b");
        mav.setViewName("index");
        return mav;
    }

    /**
     * 从商品详情内取第一张图片
     *
     * @param goods
     */
    public static void getFirstImageInGoodsContent(Goods goods) {

        //博客里的内容
        String goodsInfo = goods.getContent();
        //抓取出博客里的内容
        Document document = Jsoup.parse(goodsInfo);
        //提出.jpg图片
        Elements jpgs = document.select("img[src$=.jpg]");
        if (jpgs.size() > 0) {
            String imageName = String.valueOf(jpgs.get(0));
            int begin = imageName.indexOf("/static/images/articleImage/");
            int last = imageName.indexOf(".jpg");
            goods.setImageName(imageName.substring(begin + "/static/images/articleImage/".length(), last));
        } else {
            goods.setImageName("1");
        }
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
    public ModelAndView toContactPage(HttpSession session) {
        ModelAndView mav = new ModelAndView();
        User currentUser = (User) session.getAttribute("currentUser");
        if (currentUser == null) {
            ModelAndView mav2 = new ModelAndView("redirect:/toLoginPage");
            return mav2;
        }
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
        QueryWrapper<GoodsType> goodsTypeQueryWrapper = new QueryWrapper<>();
        goodsTypeQueryWrapper.orderByAsc("sortNum");
        List<GoodsType> goodsTypeList = goodsTypeService.list(goodsTypeQueryWrapper);
        mav.addObject("goodsTypeList", goodsTypeList);
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
    public ModelAndView toGoodsManagePage(HttpSession session, Goods searchGoods) {
        ModelAndView mav = new ModelAndView();
        User currentUser = (User) session.getAttribute("currentUser");
        QueryWrapper<Goods> goodsQueryWrapper = new QueryWrapper<>();
        goodsQueryWrapper.eq("userId", currentUser.getId());
        goodsQueryWrapper.orderByDesc("addTime");
        if (searchGoods.getName() != null) {
            goodsQueryWrapper.like("name", searchGoods.getName());
            mav.addObject("name", searchGoods.getName());
        }
        if (searchGoods.getGoodsTypeId() != null) {
            goodsQueryWrapper.eq("goodsTypeId", searchGoods.getGoodsTypeId());
            mav.addObject("goodsTypeId", searchGoods.getGoodsTypeId());
        }
        if (searchGoods.getState() != null) {
            goodsQueryWrapper.eq("state", searchGoods.getState());
            mav.addObject("state", searchGoods.getState());
        }
        if (searchGoods.getIsRecommend() != null) {
            goodsQueryWrapper.eq("isRecommend", searchGoods.getIsRecommend());
            mav.addObject("recommend", searchGoods.getIsRecommend());
        }
        List<Goods> goodsList = goodsService.list(goodsQueryWrapper);
        for (Goods goods : goodsList) {
            goods.setGoodsTypeName(goodsTypeService.findById(goods.getGoodsTypeId()).getName());
        }
        //商品分类列表
        QueryWrapper<GoodsType> goodsTypeQueryWrapper = new QueryWrapper<>();
        goodsTypeQueryWrapper.orderByAsc("sortNum");
        List<GoodsType> goodsTypeList = goodsTypeService.list(goodsTypeQueryWrapper);
        mav.addObject("goodsTypeList", goodsTypeList);
        mav.addObject("goodsList", goodsList);
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

    /**
     * ckeditor上传图片
     *
     * @param file
     * @param CKEditorFuncNum
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping("/ckeditorUpload")
    public String ckeditorUpload(@RequestParam("upload") MultipartFile file, String CKEditorFuncNum) throws Exception {
        // 获取文件名
        String fileName = file.getOriginalFilename();
        // 获取文件的后缀
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        //拼接新的文件名
        String newFileName1 = DateUtil.getCurrentDateStr2() + System.currentTimeMillis() + ".jpg";
        FileUtils.copyInputStreamToFile(file.getInputStream(), new File(articleImageFilePath + "/" + newFileName1));
        //新文件名2
        String newFileName2 = DateUtil.getCurrentDateStr2() + System.currentTimeMillis() + ".jpg";
        //压缩图片
        ImageUtil.compressImage(new File(articleImageFilePath + newFileName1), new File(articleImageFilePath + newFileName2));
        StringBuffer sb = new StringBuffer();
        sb.append("<script type=\"text/javascript\">");
        sb.append("window.parent.CKEDITOR.tools.callFunction(" + CKEditorFuncNum + ",'" + "/static/images/articleImage/" + newFileName2 + "','')");
        sb.append("</script>");
        return sb.toString();
    }

    /**
     * 跳转到求购页面
     *
     * @return
     */
    @RequestMapping("/toWantToBuyPage")
    public ModelAndView toWantToBuyPage() {
        ModelAndView mav = new ModelAndView();
        //商品分类列表
        QueryWrapper<GoodsType> goodsTypeQueryWrapper = new QueryWrapper<>();
        goodsTypeQueryWrapper.orderByAsc("sortNum");
        List<GoodsType> goodsTypeList = goodsTypeService.list(goodsTypeQueryWrapper);
        for (int i = 0; i < goodsTypeList.size(); i++) {
            if ("求购".equals(goodsTypeList.get(i).getName())) {
                goodsTypeList.remove(goodsTypeList.get(i));
                i--;
            }
        }
        mav.addObject("goodsTypeList", goodsTypeList);
        //获取推荐商品列表
        QueryWrapper<Goods> goodsQueryWrapper3 = new QueryWrapper<>();
        goodsQueryWrapper3.eq("state", 1);
        goodsQueryWrapper3.eq("isRecommend", 1);
        Page<Goods> goodsPage3 = new Page<>(1, 9);
        List<Goods> goodsRecommendList = goodsService.list(goodsPage3, goodsQueryWrapper3);
        for (Goods goods2 : goodsRecommendList) {
            getFirstImageInGoodsContent(goods2);
            goods2.setGoodsTypeName(goodsTypeService.findById(goods2.getGoodsTypeId()).getName());
        }
        Collections.shuffle(goodsRecommendList);
        mav.addObject("goodsRecommendList", goodsRecommendList);
        //获取求购列表
        QueryWrapper<Goods> goodsQueryWrapper2 = new QueryWrapper<>();
        goodsQueryWrapper2.eq("goodsTypeId", wantToBuyId);
        goodsQueryWrapper2.eq("state", 1);
        goodsQueryWrapper2.orderByDesc("addTime");
        List<Goods> goodsWantToBuyList = goodsService.list(goodsQueryWrapper2);
        mav.addObject("isWantToBuy", true);
        mav.addObject("goodsWantToBuyList", goodsWantToBuyList);
        mav.addObject("title", "用户求购--LeDao校园二手交易平台");
        mav.addObject("mainPage", "page/wantToBuy");
        mav.addObject("mainPageKey", "#b");
        mav.setViewName("index");
        return mav;
    }

    /**
     * 跳转到分类页面
     *
     * @return
     */
    @RequestMapping("/toSortPage")
    public ModelAndView toSortPage(Integer goodsTypeId) {
        ModelAndView mav = new ModelAndView();
        //获取商品列表
        QueryWrapper<Goods> goodsQueryWrapper = new QueryWrapper<>();
        goodsQueryWrapper.eq("goodsTypeId", goodsTypeId);
        goodsQueryWrapper.eq("state", 1);
        Page<Goods> goodsPage = new Page<>(1, 6);
        List<Goods> goodsList = goodsService.list(goodsPage, goodsQueryWrapper);
        for (Goods goods : goodsList) {
            getFirstImageInGoodsContent(goods);
        }
        mav.addObject("goodsList", goodsList);
        //商品分类列表
        QueryWrapper<GoodsType> goodsTypeQueryWrapper = new QueryWrapper<>();
        goodsTypeQueryWrapper.orderByAsc("sortNum");
        List<GoodsType> goodsTypeList = goodsTypeService.list(goodsTypeQueryWrapper);
        for (int i = 0; i < goodsTypeList.size(); i++) {
            if ("求购".equals(goodsTypeList.get(i).getName())) {
                goodsTypeList.remove(goodsTypeList.get(i));
                i--;
            }
        }
        mav.addObject("goodsTypeList", goodsTypeList);
        //获取推荐商品列表
        QueryWrapper<Goods> goodsQueryWrapper3 = new QueryWrapper<>();
        goodsQueryWrapper3.eq("isRecommend", 1);
        goodsQueryWrapper3.eq("state", 1);
        Page<Goods> goodsPage3 = new Page<>(1, 9);
        List<Goods> goodsRecommendList = goodsService.list(goodsPage3, goodsQueryWrapper3);
        for (Goods goods2 : goodsRecommendList) {
            getFirstImageInGoodsContent(goods2);
            goods2.setGoodsTypeName(goodsTypeService.findById(goods2.getGoodsTypeId()).getName());
        }
        Collections.shuffle(goodsRecommendList);
        mav.addObject("goodsRecommendList", goodsRecommendList);
        mav.addObject("isSort", true);
        mav.addObject("title", "分类--LeDao校园二手交易平台");
        mav.addObject("mainPage", "page/sortPage");
        mav.addObject("mainPageKey", "#b");
        mav.setViewName("index");
        return mav;
    }

    /**
     * 跳转到我的购物车界面
     *
     * @param session
     * @return
     */
    @RequestMapping("/toMyShoppingCart")
    public ModelAndView toMyShoppingCart(HttpSession session) {
        ModelAndView mav = new ModelAndView();
        Gson gson = new Gson();
        User currentUser = (User) session.getAttribute("currentUser");
        if (currentUser == null) {
            ModelAndView mav2 = new ModelAndView("redirect:/toLoginPage");
            return mav2;
        }
        String shoppingCartName = currentUser.getId() + "_shoppingCart";
        List<String> shoppingCartGoodsStr = RedisUtil.listRange(shoppingCartName, 0L, -1L);
        List<Goods> shoppingCartGoodsList = new ArrayList<>();
        for (String s : shoppingCartGoodsStr) {
            Goods goods = gson.fromJson(s, Goods.class);
            shoppingCartGoodsList.add(goods);
            getFirstImageInGoodsContent(goods);
        }
        mav.addObject("shoppingCartGoodsList", shoppingCartGoodsList);
        mav.addObject("shoppingCartGoodsListSize", shoppingCartGoodsList.size());
        //商品分类列表
        QueryWrapper<GoodsType> goodsTypeQueryWrapper = new QueryWrapper<>();
        goodsTypeQueryWrapper.orderByAsc("sortNum");
        List<GoodsType> goodsTypeList = goodsTypeService.list(goodsTypeQueryWrapper);
        for (int i = 0; i < goodsTypeList.size(); i++) {
            if ("求购".equals(goodsTypeList.get(i).getName())) {
                goodsTypeList.remove(goodsTypeList.get(i));
                i--;
            }
        }
        mav.addObject("goodsTypeList", goodsTypeList);
        //获取推荐商品列表
        QueryWrapper<Goods> goodsQueryWrapper3 = new QueryWrapper<>();
        goodsQueryWrapper3.eq("isRecommend", 1);
        goodsQueryWrapper3.eq("state", 1);
        Page<Goods> goodsPage3 = new Page<>(1, 9);
        List<Goods> goodsRecommendList = goodsService.list(goodsPage3, goodsQueryWrapper3);
        for (Goods goods2 : goodsRecommendList) {
            getFirstImageInGoodsContent(goods2);
            goods2.setGoodsTypeName(goodsTypeService.findById(goods2.getGoodsTypeId()).getName());
        }
        Collections.shuffle(goodsRecommendList);
        mav.addObject("goodsRecommendList", goodsRecommendList);
        mav.addObject("title", "我的购物车--LeDao校园二手交易平台");
        mav.addObject("mainPage", "page/myShoppingCart");
        mav.addObject("mainPageKey", "#b");
        mav.setViewName("index");
        return mav;
    }
}
