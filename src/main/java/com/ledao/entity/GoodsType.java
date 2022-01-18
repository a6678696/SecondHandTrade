package com.ledao.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 商品类别实体类
 *
 * @author LeDao
 * @company
 * @create 2022-01-15 21:08
 */
@Data
@TableName(value = "t_goods_type")
public class GoodsType {

    /**
     * 编号
     */
    @TableId
    private Integer id;
    /**
     * 名称
     */
    private String name;
    /**
     * 排序
     */
    @TableField(value = "sortNum")
    private Integer sortNum;
    /**
     * 分类下商品数量
     */
    @TableField(exist = false)
    private Integer goodsNum;
}
