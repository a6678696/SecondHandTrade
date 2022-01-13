package com.ledao.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ledao.entity.Contact;
import com.ledao.mapper.ContactMapper;
import com.ledao.service.ContactService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 联系Service接口实现类
 *
 * @author LeDao
 * @company
 * @create 2022-01-12 13:47
 */
@Service("contactService")
public class ContactServiceImpl implements ContactService {

    @Resource
    private ContactMapper contactMapper;

    @Override
    public List<Contact> list(QueryWrapper<Contact> contactQueryWrapper, Page<Contact> contactPage) {
        return contactMapper.selectPage(contactPage, contactQueryWrapper).getRecords();
    }

    @Override
    public Integer getCount(QueryWrapper<Contact> contactQueryWrapper) {
        return contactMapper.selectCount(contactQueryWrapper);
    }

    @Override
    public int add(Contact contact) {
        return contactMapper.insert(contact);
    }

    @Override
    public int update(Contact contact) {
        return contactMapper.updateById(contact);
    }

    @Override
    public int delete(Integer id) {
        return contactMapper.deleteById(id);
    }

    @Override
    public Contact findById(Integer id) {
        return contactMapper.selectById(id);
    }
}
