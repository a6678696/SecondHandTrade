package com.ledao.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 支付记录实体类
 *
 * @author LeDao
 * @company
 * @create 2022-01-17 3:30
 */
@Data
@TableName(value = "t_pay_record")
public class PayRecord {

    /**
     * 编号
     */
    private Integer id;
    /**
     * 商品id
     */
    @TableField(value = "goodsId")
    private Integer goodsId;
    /**
     * 商品名称,用于搜索
     */
    @TableField(exist = false)
    private String goodsName;
    /**
     * 支付人id
     */
    @TableField(value = "userId")
    private Integer userId;
    /**
     * 用户名,用于搜索
     */
    @TableField(exist = false)
    private String userName;
    /**
     * 支付时间
     */
    @TableField(value = "payTime")
    private Date payTime;
}
