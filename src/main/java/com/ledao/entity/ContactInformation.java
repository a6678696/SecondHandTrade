package com.ledao.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 联系方式实体类
 *
 * @author LeDao
 * @company
 * @create 2022-01-12 17:07
 */
@Data
@TableName(value = "t_contact_information")
public class ContactInformation {

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
     * 内容
     */
    private String content;
    /**
     * 用户id
     */
    @TableField(value = "userId")
    private Integer userId;
    /**
     * 用户实体
     */
    @TableField(exist = false)
    private User user;
    /**
     * 用户名，用于搜索
     */
    @TableField(exist = false)
    private String userName;
}
