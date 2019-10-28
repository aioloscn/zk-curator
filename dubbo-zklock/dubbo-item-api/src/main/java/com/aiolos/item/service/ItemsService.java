package com.aiolos.item.service;

import com.aiolos.item.pojo.Items;

/**
 * @author Aiolos
 * @date 2019-10-24 14:49
 */
public interface ItemsService {

    /**
     * @Description: 根据商品id获取商品
     */
    Items getItem(String itemId);

    /**
     * @Description: 查询商品库存
     */
    int getItemCounts(String itemId);

    /**
     * @Description: 购买商品成功后减少库存
     */
    void displayReduceCounts(String itemId, int buyCounts);
}
