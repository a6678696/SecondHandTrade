package com.ledao.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ledao.entity.User;

import java.util.List;

/**
 * 用户Service接口
 *
 * @author LeDao
 * @company
 * @create 2022-01-03 12:10
 */
public interface UserService {


    /**
     * 分页条件查询用户
     *
     * @param userQueryWrapper
     * @param userPage
     * @return
     */
    List<User> list(QueryWrapper<User> userQueryWrapper, Page<User> userPage);

    /**
     * 不分页条件查询用户
     *
     * @param userQueryWrapper
     * @return
     */
    List<User> list(QueryWrapper<User> userQueryWrapper);

    /**
     * 获取记录数
     *
     * @param userQueryWrapper
     * @return
     */
    Integer getCount(QueryWrapper<User> userQueryWrapper);

    /**
     * 根据用户名查找用户
     *
     * @param userName
     * @return
     */
    User findByUserName(String userName);

    /**
     * 根据邮箱查找用户
     *
     * @param email
     * @return
     */
    User findByEmail(String email);

    /**
     * 根据id查找用户
     *
     * @param id
     * @return
     */
    User findById(Integer id);

    /**
     * 添加用户
     *
     * @param user
     * @return
     */
    int add(User user);

    /**
     * 更新用户信息
     *
     * @param user
     * @return
     */
    int update(User user);

    /**
     * 根据id删除用户
     *
     * @param id
     * @return
     */
    int delete(Integer id);
}
