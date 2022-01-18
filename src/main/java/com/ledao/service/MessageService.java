package com.ledao.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ledao.entity.Message;

import java.util.List;

/**
 * 消息Service接口
 *
 * @author LeDao
 * @company
 * @create 2022-01-18 12:17
 */
public interface MessageService {

    /**
     * 分页条件获取消息
     *
     * @param messagePage
     * @param messageQueryWrapper
     * @return
     */
    List<Message> list(Page<Message> messagePage, QueryWrapper<Message> messageQueryWrapper);

    /**
     * 不分页条件获取消息
     *
     * @param messageQueryWrapper
     * @return
     */
    List<Message> list(QueryWrapper<Message> messageQueryWrapper);

    /**
     * 获取记录数
     *
     * @param messageQueryWrapper
     * @return
     */
    Integer getCount(QueryWrapper<Message> messageQueryWrapper);

    /**
     * 添加消息
     *
     * @param message
     * @return
     */
    int add(Message message);

    /**
     * 根据id删除消息
     *
     * @param id
     * @return
     */
    int deleteById(Integer id);
}
