package com.ledao.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 消息实体类
 *
 * @author LeDao
 * @company
 * @create 2022-01-18 12:15
 */
@Data
@TableName(value = "t_message")
public class Message {

    /**
     * 编号
     */
    @TableId
    private Integer id;
    /**
     * 接收人id
     */
    @TableField(value = "userId")
    private Integer userId;
    /**
     * 用户名,用于搜索
     */
    @TableField(exist = false)
    private String userName;
    /**
     * 内容
     */
    private String content;
    /**
     * 添加时间
     */
    private Date time;
    /**
     * 是否已读,0表未读,1表示已读
     */
    @TableField(value = "isRead")
    private Integer isRead;
}
