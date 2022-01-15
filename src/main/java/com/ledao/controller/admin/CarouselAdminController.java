package com.ledao.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ledao.entity.Carousel;
import com.ledao.service.CarouselService;
import com.ledao.util.DateUtil;
import com.ledao.util.ImageUtil;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 后台轮播图Controller层
 *
 * @author LeDao
 * @company
 * @create 2022-01-14 20:34
 */
@RestController
@RequestMapping("/admin/carousel")
public class CarouselAdminController {

    @Value("${carouselImageFilePath}")
    private String carouselImageFilePath;

    @Resource
    private CarouselService carouselService;

    /**
     * 分页条件查询轮播图
     *
     * @param carousel
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping("/list")
    public Map<String, Object> list(Carousel carousel, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "rows", required = false) Integer rows) {
        Map<String, Object> resultMap = new HashMap<>(16);
        QueryWrapper<Carousel> carouselQueryWrapper = new QueryWrapper<>();
        if (carousel.getTitle() != null) {
            carouselQueryWrapper.like("title", carousel.getTitle());
        }
        carouselQueryWrapper.orderByAsc("sortNum");
        Page<Carousel> carouselPage = new Page<>(page, rows);
        List<Carousel> carouselList = carouselService.list(carouselQueryWrapper, carouselPage);
        Integer total = carouselService.getCount(carouselQueryWrapper);
        resultMap.put("rows", carouselList);
        resultMap.put("total", total);
        return resultMap;
    }

    /**
     * 添加或修改轮播图
     *
     * @param carousel
     * @param file
     * @return
     * @throws Exception
     */
    @RequestMapping("/save")
    public Map<String, Object> save(Carousel carousel, @RequestParam(value = "carouselImage") MultipartFile file) throws Exception {
        Map<String, Object> resultMap = new HashMap<>(16);
        if (file.isEmpty() && carousel.getId() == null) {
            resultMap.put("success", false);
            resultMap.put("errorInfo", "请选择轮播图的图片!!");
            return resultMap;
        }
        //上传的图片存在时
        if (!file.isEmpty()) {
            //修改时,删除图片
            if (carousel.getId() != null) {
                FileUtils.deleteQuietly(new File(carouselImageFilePath + carouselService.findById(carousel.getId()).getImageName()));
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
            FileUtils.copyInputStreamToFile(file.getInputStream(), new File(carouselImageFilePath + newFileName1));
            //新文件名2
            String newFileName2 = DateUtil.getCurrentDateStr2() + System.currentTimeMillis() + "." + suffixName;
            //压缩图片
            ImageUtil.compressImage(new File(carouselImageFilePath + newFileName1), new File(carouselImageFilePath + newFileName2));
            carousel.setImageName(newFileName2);
        }
        if ("".equals(carousel.getUrl())) {
            carousel.setUrl(null);
        }
        int key;
        if (carousel.getId() == null) {
            key = carouselService.add(carousel);
        } else {
            key = carouselService.update(carousel);
        }
        if (key > 0) {
            resultMap.put("success", true);
        } else {
            resultMap.put("success", false);
        }
        return resultMap;
    }

    /**
     * 删除轮播图,可批量删除
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
            FileUtils.deleteQuietly(new File(carouselImageFilePath + carouselService.findById(carouselService.findById(id).getId()).getImageName()));
            carouselService.deleteById(id);
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
