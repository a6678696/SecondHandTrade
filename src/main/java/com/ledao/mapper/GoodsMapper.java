package com.ledao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ledao.entity.Goods;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 商品Mapper接口
 *
 * @author LeDao
 * @company
 * @create 2022-01-15 22:12
 */
public interface GoodsMapper extends BaseMapper<Goods> {

    /**
     * 获取推荐时间过期的商品id集合
     *
     * @return
     */
    @Select("SELECT id FROM t_goods WHERE (SELECT TIMESTAMPDIFF (SECOND,recommendTime,(SELECT ADDDATE(NOW(),INTERVAL -recommendDays DAY))))>=0 AND isRecommend=1;")
    List<Integer> getGoodsIdRecommendTimeExpired();
}
