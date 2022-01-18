package com.ledao.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ledao.entity.ReserveRecord;

import java.util.List;

/**
 * 预订记录Service接口
 *
 * @author LeDao
 * @company
 * @create 2022-01-17 3:37
 */
public interface ReserveRecordService {

    /**
     * 分页条件查询预订记录
     *
     * @param reserveRecordPage
     * @param reserveRecordQueryWrapper
     * @return
     */
    List<ReserveRecord> list(Page<ReserveRecord> reserveRecordPage, QueryWrapper<ReserveRecord> reserveRecordQueryWrapper);

    /**
     * 不分页条件查询预订记录
     *
     * @param reserveRecordQueryWrapper
     * @return
     */
    List<ReserveRecord> list(QueryWrapper<ReserveRecord> reserveRecordQueryWrapper);

    /**
     * 获取记录数
     *
     * @param reserveRecordQueryWrapper
     * @return
     */
    Integer getCount(QueryWrapper<ReserveRecord> reserveRecordQueryWrapper);

    /**
     * 添加预订记录
     *
     * @param reserveRecord
     * @return
     */
    int add(ReserveRecord reserveRecord);

    /**
     * 根据id查找预订记录
     *
     * @param id
     * @return
     */
    ReserveRecord findById(Integer id);

    /**
     * 根据商品id查找预订记录
     *
     * @param goodsId
     * @return
     */
    ReserveRecord findByGoodsId(Integer goodsId);

    /**
     * 根据商品id查找预订记录
     *
     * @param goodsId
     * @param state
     * @return
     */
    ReserveRecord findByGoodsIdAndState(Integer goodsId, Integer state);

    /**
     * 根据id删除预订记录
     *
     * @param id
     * @return
     */
    int deleteById(Integer id);

    /**
     * 修改预订记录
     *
     * @param reserveRecord
     * @return
     */
    int update(ReserveRecord reserveRecord);
}
