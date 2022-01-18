package com.ledao.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ledao.entity.ReserveRecord;
import com.ledao.mapper.ReserveRecordMapper;
import com.ledao.service.ReserveRecordService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 预订记录Service接口实现类
 *
 * @author LeDao
 * @company
 * @create 2022-01-17 3:37
 */
@Service("reserveRecordService")
public class ReserveRecordServiceImpl implements ReserveRecordService {

    @Resource
    private ReserveRecordMapper reserveRecordMapper;

    @Override
    public List<ReserveRecord> list(Page<ReserveRecord> reserveRecordPage, QueryWrapper<ReserveRecord> reserveRecordQueryWrapper) {
        return reserveRecordMapper.selectPage(reserveRecordPage, reserveRecordQueryWrapper).getRecords();
    }

    @Override
    public List<ReserveRecord> list(QueryWrapper<ReserveRecord> reserveRecordQueryWrapper) {
        return reserveRecordMapper.selectList(reserveRecordQueryWrapper);
    }

    @Override
    public Integer getCount(QueryWrapper<ReserveRecord> reserveRecordQueryWrapper) {
        return reserveRecordMapper.selectCount(reserveRecordQueryWrapper);
    }

    @Override
    public int add(ReserveRecord reserveRecord) {
        return reserveRecordMapper.insert(reserveRecord);
    }

    @Override
    public ReserveRecord findById(Integer id) {
        return reserveRecordMapper.selectById(id);
    }

    @Override
    public ReserveRecord findByGoodsId(Integer goodsId) {
        QueryWrapper<ReserveRecord> reserveRecordQueryWrapper = new QueryWrapper<>();
        reserveRecordQueryWrapper.eq("goodsId", goodsId);
        return reserveRecordMapper.selectOne(reserveRecordQueryWrapper);
    }

    @Override
    public ReserveRecord findByGoodsIdAndState(Integer goodsId, Integer state) {
        QueryWrapper<ReserveRecord> reserveRecordQueryWrapper = new QueryWrapper<>();
        reserveRecordQueryWrapper.eq("goodsId", goodsId);
        reserveRecordQueryWrapper.eq("state", state);
        return reserveRecordMapper.selectOne(reserveRecordQueryWrapper);
    }

    @Override
    public int deleteById(Integer id) {
        return reserveRecordMapper.deleteById(id);
    }

    @Override
    public int update(ReserveRecord reserveRecord) {
        return reserveRecordMapper.updateById(reserveRecord);
    }
}
