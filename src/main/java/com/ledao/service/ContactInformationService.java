package com.ledao.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ledao.entity.ContactInformation;
import com.ledao.entity.User;

import java.util.List;

/**
 * 联系方式Service接口
 *
 * @author LeDao
 * @company
 * @create 2022-01-12 17:17
 */
public interface ContactInformationService {

    /**
     * 分页条件查询联系方式
     *
     * @param contactInformationQueryWrapper
     * @param contactInformationPage
     * @return
     */
    List<ContactInformation> list(QueryWrapper<ContactInformation> contactInformationQueryWrapper, Page<ContactInformation> contactInformationPage);

    /**
     * 获取记录数
     *
     * @param contactInformationQueryWrapper
     * @return
     */
    Integer getCount(QueryWrapper<ContactInformation> contactInformationQueryWrapper);

    /**
     * 添加联系方式
     *
     * @param contactInformation
     * @return
     */
    int add(ContactInformation contactInformation);

    /**
     * 修改联系方式
     *
     * @param contactInformation
     * @return
     */
    int update(ContactInformation contactInformation);

    /**
     * 根据名称和用户id查找联系方式
     *
     * @param name
     * @param userId
     * @return
     */
    ContactInformation findByNameAndUserId(String name, Integer userId);

    /**
     * 根据id查找联系方式
     *
     * @param id
     * @return
     */
    ContactInformation findById(Integer id);

    /**
     * 根据id删除联系方式
     *
     * @param id
     * @return
     */
    int deleteById(Integer id);
}
