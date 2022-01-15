package com.ledao.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ledao.entity.ContactInformation;
import com.ledao.mapper.ContactInformationMapper;
import com.ledao.service.ContactInformationService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 联系方式Service接口实现类
 *
 * @author LeDao
 * @company
 * @create 2022-01-12 17:19
 */
@Service("contactInformationService")
public class ContactInformationServiceImpl implements ContactInformationService {

    @Resource
    private ContactInformationMapper contactInformationMapper;

    @Override
    public List<ContactInformation> list(QueryWrapper<ContactInformation> contactInformationQueryWrapper, Page<ContactInformation> contactInformationPage) {
        return contactInformationMapper.selectPage(contactInformationPage,contactInformationQueryWrapper).getRecords();
    }

    @Override
    public List<ContactInformation> list(QueryWrapper<ContactInformation> contactInformationQueryWrapper) {
        return contactInformationMapper.selectList(contactInformationQueryWrapper);
    }

    @Override
    public Integer getCount(QueryWrapper<ContactInformation> contactInformationQueryWrapper) {
        return contactInformationMapper.selectCount(contactInformationQueryWrapper);
    }

    @Override
    public int add(ContactInformation contactInformation) {
        return contactInformationMapper.insert(contactInformation);
    }

    @Override
    public int update(ContactInformation contactInformation) {
        return contactInformationMapper.updateById(contactInformation);
    }

    @Override
    public ContactInformation findByNameAndUserId(String name,Integer userId) {
        QueryWrapper<ContactInformation> contactInformationQueryWrapper = new QueryWrapper<>();
        contactInformationQueryWrapper.eq("name", name);
        contactInformationQueryWrapper.eq("userId", userId);
        return contactInformationMapper.selectOne(contactInformationQueryWrapper);
    }

    @Override
    public ContactInformation findById(Integer id) {
        return contactInformationMapper.selectById(id);
    }

    @Override
    public int deleteById(Integer id) {
        return contactInformationMapper.deleteById(id);
    }
}
