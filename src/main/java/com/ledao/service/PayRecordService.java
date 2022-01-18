package com.ledao.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ledao.entity.PayRecord;

import java.util.List;

/**
 * 支付记录Service接口
 *
 * @author LeDao
 * @company
 * @create 2022-01-17 3:37
 */
public interface PayRecordService {

    /**
     * 分页条件查询支付记录
     *
     * @param payRecordPage
     * @param payRecordQueryWrapper
     * @return
     */
    List<PayRecord> list(Page<PayRecord> payRecordPage, QueryWrapper<PayRecord> payRecordQueryWrapper);

    /**
     * 不分页条件查询支付记录
     *
     * @param payRecordQueryWrapper
     * @return
     */
    List<PayRecord> list(QueryWrapper<PayRecord> payRecordQueryWrapper);

    /**
     * 获取记录数
     *
     * @param payRecordQueryWrapper
     * @return
     */
    Integer getCount(QueryWrapper<PayRecord> payRecordQueryWrapper);

    /**
     * 添加支付记录
     *
     * @param payRecord
     * @return
     */
    int add(PayRecord payRecord);

    /**
     * 根据id删除支付记录
     *
     * @param id
     * @return
     */
    PayRecord findById(Integer id);

    /**
     * 根据id删除支付记录
     *
     * @param id
     * @return
     */
    int deleteById(Integer id);
}
