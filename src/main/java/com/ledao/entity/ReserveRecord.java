package com.ledao.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 预定记录实体类
 *
 * @author LeDao
 * @company
 * @create 2022-01-17 3:30
 */
@Data
@TableName(value = "t_reserve_record")
public class ReserveRecord {

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
     * 预定人id
     */
    @TableField(value = "userId")
    private Integer userId;
    /**
     * 用户名,用于搜索
     */
    @TableField(exist = false)
    private String userName;
    /**
     * 预定时间
     */
    @TableField(value = "reserveTime")
    private Date reserveTime;
    /**
     * 是否取消,0代表未取消,1代表已取消
     */
    @TableField(value = "isCancel")
    private Integer isCancel;
}
