package com.ledao.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ledao.entity.PayRecord;
import com.ledao.mapper.PayRecordMapper;
import com.ledao.service.PayRecordService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 支付记录Service接口实现类
 *
 * @author LeDao
 * @company
 * @create 2022-01-17 3:37
 */
@Service("payRecordService")
public class PayRecordServiceImpl implements PayRecordService {

    @Resource
    private PayRecordMapper payRecordMapper;

    @Override
    public List<PayRecord> list(Page<PayRecord> payRecordPage, QueryWrapper<PayRecord> payRecordQueryWrapper) {
        return payRecordMapper.selectPage(payRecordPage, payRecordQueryWrapper).getRecords();
    }

    @Override
    public List<PayRecord> list(QueryWrapper<PayRecord> payRecordQueryWrapper) {
        return payRecordMapper.selectList(payRecordQueryWrapper);
    }

    @Override
    public Integer getCount(QueryWrapper<PayRecord> payRecordQueryWrapper) {
        return payRecordMapper.selectCount(payRecordQueryWrapper);
    }

    @Override
    public int add(PayRecord payRecord) {
        return payRecordMapper.insert(payRecord);
    }

    @Override
    public PayRecord findById(Integer id) {
        return payRecordMapper.selectById(id);
    }

    @Override
    public int deleteById(Integer id) {
        return payRecordMapper.deleteById(id);
    }
}
