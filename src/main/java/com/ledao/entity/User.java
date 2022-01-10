package com.ledao.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 用户实体类
 *
 * @author LeDao
 * @company
 * @create 2022-01-03 11:50
 */
@Data
@TableName(value = "t_user")
public class User {

    /**
     * 编号
     */
    @TableId
    private Integer id;
    /**
     * 用户名
     */
    @TableField(value = "userName")
    private String userName;
    /**
     * 密码
     */
    private String password;
    /**
     * 昵称
     */
    @TableField(value = "nickName")
    private String nickName;
    /**
     * 用户类型:1为管理员,2为普通用户
     */
    private Integer type;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 用户状态:1为正常,0为封禁
     */
    private Integer status;
    /**
     * 验证码
     */
    @TableField(exist = false)
    private String imageCode;
    /**
     * 头像名称
     */
    @TableField(value = "imageName")
    private String imageName;
}
