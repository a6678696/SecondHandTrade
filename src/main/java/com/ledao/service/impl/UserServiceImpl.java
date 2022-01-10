package com.ledao.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ledao.entity.User;
import com.ledao.mapper.UserMapper;
import com.ledao.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 用户Service接口实现类
 *
 * @author LeDao
 * @company
 * @create 2022-01-03 12:13
 */
@Service("UserService")
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public List<User> list(QueryWrapper<User> userQueryWrapper, Page<User> userPage) {
        return userMapper.selectPage(userPage,userQueryWrapper).getRecords();
    }

    @Override
    public Integer getCount(QueryWrapper<User> userQueryWrapper) {
        return userMapper.selectCount(userQueryWrapper);
    }

    @Override
    public User findByUserName(String userName) {
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("userName", userName);
        return userMapper.selectOne(userQueryWrapper);
    }

    @Override
    public User findByEmail(String email) {
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("email", email);
        return userMapper.selectOne(userQueryWrapper);
    }

    @Override
    public User findById(Integer id) {
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("id", id);
        return userMapper.selectOne(userQueryWrapper);
    }

    @Override
    public int add(User user) {
        return userMapper.insert(user);
    }

    @Override
    public int update(User user) {
        return userMapper.updateById(user);
    }

    @Override
    public int delete(Integer id) {
        return userMapper.deleteById(id);
    }
}
