package com.ledao.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ledao.entity.GoodsType;
import com.ledao.service.GoodsTypeService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 后台商品类别Controller层
 *
 * @author LeDao
 * @company
 * @create 2022-01-15 21:17
 */
@RestController
@RequestMapping("/admin/goodsType")
public class GoodsTypeAdminController {

    @Resource
    private GoodsTypeService goodsTypeService;

    /**
     * 分页条件查询商品类别
     *
     * @param goodsType
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping("/list")
    public Map<String, Object> list(GoodsType goodsType, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "rows", required = false) Integer rows) {
        Map<String, Object> resultMap = new HashMap<>(16);
        QueryWrapper<GoodsType> goodsTypeQueryWrapper = new QueryWrapper<>();
        if (goodsType.getName() != null) {
            goodsTypeQueryWrapper.like("name", goodsType.getName());
        }
        goodsTypeQueryWrapper.orderByAsc("sortNum");
        Page<GoodsType> goodsTypePage = new Page<>(page, rows);
        List<GoodsType> goodsTypeList = goodsTypeService.list(goodsTypePage, goodsTypeQueryWrapper);
        Integer total = goodsTypeService.getCount(goodsTypeQueryWrapper);
        resultMap.put("rows", goodsTypeList);
        resultMap.put("total", total);
        return resultMap;
    }

    /**
     * 添加或修改商品类别
     *
     * @param goodsType
     * @return
     */
    @RequestMapping("/save")
    public Map<String, Object> save(GoodsType goodsType) {
        Map<String, Object> resultMap = new HashMap<>(16);
        int key;
        if (goodsType.getId() == null) {
            key = goodsTypeService.add(goodsType);
        } else {
            key = goodsTypeService.update(goodsType);
        }
        if (key > 0) {
            resultMap.put("success", true);
        } else {
            resultMap.put("success", false);
        }
        return resultMap;
    }

    /**
     * 删除商品类别,可批量删除
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
            goodsTypeService.deleteById(id);
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
