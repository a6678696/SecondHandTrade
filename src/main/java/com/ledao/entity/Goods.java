package com.ledao.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 商品实体类
 *
 * @author LeDao
 * @company
 * @create 2022-01-15 22:02
 */
@Data
@TableName(value = "t_goods")
public class Goods {

    /**
     * 编号
     */
    private Integer id;
    /**
     * 商品名称
     */
    private String name;
    /**
     * 商品详情
     */
    private String content;
    /**
     * 现在价格
     */
    @TableField(value = "priceNow")
    private double priceNow;
    /**
     * 上次价格
     */
    @TableField(value = "priceLast")
    private double priceLast;
    /**
     * 商品状态,0为未审核,1为上架中,2为审核不通过,3为已下架,4为被预订,5为交易成功
     */
    private Integer state;
    /**
     * 审核不通过的理由
     */
    private String reason;
    /**
     * 商品类别id
     */
    @TableField(value = "goodsTypeId")
    private Integer goodsTypeId;
    /**
     * 发布者id
     */
    @TableField(value = "userId")
    private Integer userId;
    /**
     * 发布者实体
     */
    @TableField(exist = false)
    private User user;
    /**
     * 用户名,用于搜索
     */
    @TableField(exist = false)
    private String userName;
    /**
     * 联系方式列表
     */
    @TableField(exist = false)
    private List<ContactInformation> contactInformationList;
    /**
     * 添加时间
     */
    @TableField(value = "addTime")
    private Date addTime;
    /**
     * 是否推荐,0为不推荐(默认),1为推荐
     */
    @TableField(value = "isRecommend")
    private Integer isRecommend;
    /**
     * 推荐时间
     */
    @TableField(value = "recommendTime")
    private Date recommendTime;
    /**
     * 推荐天数
     */
    @TableField(value = "recommendDays")
    private Integer recommendDays;
    /**
     * 从商品详情内取第一张图片作为展示
     */
    @TableField(exist = false)
    private String imageName;
    /**
     * 点击数
     */
    private Integer click;
    /**
     * 商品类别名称
     */
    @TableField(exist = false)
    private String goodsTypeName;
}
