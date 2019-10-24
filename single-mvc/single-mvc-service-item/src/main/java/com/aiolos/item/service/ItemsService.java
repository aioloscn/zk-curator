package com.aiolos.item.service;

import com.aiolos.pojo.Items;

/**
 * @author Aiolos
 * @date 2019-10-24 14:49
 */
public interface ItemsService {

    /**
     * @Description: 根据商品id获取商品
     */
    public Items getItem(String itemId);

    /**
     * @Description: 查询商品库存
     */
    public int getItemCounts(String itemId);

    /**
     * @Description: 购买商品成功后减少库存
     */
    public void displayReduceCounts(String itemId, int buyCounts);
}
