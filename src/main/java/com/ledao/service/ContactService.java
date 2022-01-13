package com.ledao.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ledao.entity.Contact;

import java.util.List;

/**
 * 留言Service接口
 *
 * @author LeDao
 * @company
 * @create 2022-01-12 13:46
 */
public interface ContactService {

    /**
     * 分页条件查询留言
     *
     * @param contactQueryWrapper
     * @param contactPage
     * @return
     */
    List<Contact> list(QueryWrapper<Contact> contactQueryWrapper, Page<Contact> contactPage);

    /**
     * 获取记录数
     *
     * @param contactQueryWrapper
     * @return
     */
    Integer getCount(QueryWrapper<Contact> contactQueryWrapper);

    /**
     * 添加留言
     *
     * @param contact
     * @return
     */
    int add(Contact contact);

    /**
     * 修改留言
     *
     * @param contact
     * @return
     */
    int update(Contact contact);

    /**
     * 根据id删除留言
     *
     * @param id
     * @return
     */
    int delete(Integer id);

    /**
     * 根据id查找留言
     *
     * @param id
     * @return
     */
    Contact findById(Integer id);
}
