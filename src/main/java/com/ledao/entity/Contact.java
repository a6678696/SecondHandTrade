package com.ledao.entity;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 留言实体类
 *
 * @author LeDao
 * @company
 * @create 2022-01-12 13:40
 */
@Data
@TableName(value = "t_contact")
public class Contact {

    /**
     * 编号
     */
    @TableId
    private Integer id;
    /**
     * 留言内容
     */
    private String content;
    /**
     * 答复
     */
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private String reply;
    /**
     * 留言时间
     */
    private Date time;
    /**
     * 留言用户id（普通用户）
     */
    @TableField(value = "userId")
    private Integer userId;
    /**
     * 回复用户id（管理员）
     */
    @TableField(value = "userIdReply")
    private Integer userIdReply;
    /**
     * 用户名
     */
    @TableField(exist = false)
    private String userName;
}
