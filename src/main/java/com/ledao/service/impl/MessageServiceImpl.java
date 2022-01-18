package com.ledao.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ledao.entity.Message;
import com.ledao.mapper.MessageMapper;
import com.ledao.service.MessageService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 消息Service接口实现类
 *
 * @author LeDao
 * @company
 * @create 2022-01-18 12:18
 */
@Service("messageService")
public class MessageServiceImpl implements MessageService {

    @Resource
    private MessageMapper messageMapper;

    @Override
    public List<Message> list(Page<Message> messagePage, QueryWrapper<Message> messageQueryWrapper) {
        return messageMapper.selectPage(messagePage, messageQueryWrapper).getRecords();
    }

    @Override
    public List<Message> list(QueryWrapper<Message> messageQueryWrapper) {
        return messageMapper.selectList(messageQueryWrapper);
    }

    @Override
    public Integer getCount(QueryWrapper<Message> messageQueryWrapper) {
        return messageMapper.selectCount(messageQueryWrapper);
    }

    @Override
    public int add(Message message) {
        return messageMapper.insert(message);
    }

    @Override
    public int deleteById(Integer id) {
        return messageMapper.deleteById(id);
    }
}
