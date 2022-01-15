package com.ledao.entity;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 轮播图实体类
 *
 * @author LeDao
 * @company
 * @create 2022-01-13 13:17
 */
@Data
@TableName(value = "t_carousel")
public class Carousel {

    /**
     * 编号
     */
    @TableId
    private Integer id;
    /**
     * 图片名称
     */
    @TableField(value = "imageName")
    private String imageName;
    /**
     * 标题
     */
    private String title;
    /**
     * 描述
     */
    private String content;
    /**
     * 排列顺序
     */
    @TableField(value = "sortNum")
    private Integer sortNum;
    /**
     * 链接
     */
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private String url;
}
