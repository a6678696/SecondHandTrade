package com.ledao.quartz;

import com.ledao.entity.Goods;
import com.ledao.service.GoodsService;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.Resource;
import java.util.List;

/**
 * 商品推荐时间过期后,将商品的推荐状态由 推荐 设置为 不推荐
 *
 * @author LeDao
 * @company
 * @create 2022-01-19 14:06
 */
@Configuration
@EnableScheduling
public class SetGoodsNotRecommend {

    @Resource
    private GoodsService goodsService;

    /**
     * 每隔60分钟执行一次
     */
    @Scheduled(cron = "0 */60 * * * ?")
    public void work(){
        //获取推荐时间过期的商品id集合
        List<Integer> goodsIdList= goodsService.getGoodsIdRecommendTimeExpired();
        for (Integer id : goodsIdList) {
            Goods goods = goodsService.findById(id);
            goods.setIsRecommend(0);
            goodsService.update(goods);
        }
    }
}
