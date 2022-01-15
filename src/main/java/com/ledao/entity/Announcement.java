package com.ledao.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 公告实体类
 *
 * @author LeDao
 * @company
 * @create 2022-01-15 13:35
 */
@Data
@TableName(value = "t_announcement")
public class Announcement {

    /**
     * 编号
     */
    @TableId
    private Integer id;
    /**
     *标题
     */
    private String title;
    /**
     * 内容
     */
    private String content;
    /**
     * 发布时间
     */
    private Date time;
    /**
     * 点击数
     */
    private Integer click;
    /**
     * 排序
     */
    @TableField(value = "sortNum")
    private Integer sortNum;
}
