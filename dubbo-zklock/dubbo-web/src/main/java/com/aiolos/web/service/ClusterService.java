package com.aiolos.web.service;

/**
 * @author Aiolos
 * @date 2019-10-28 22:45
 */
public interface ClusterService {

    /**
     * @Description: 用户购买商品，减少库存，生成订单
     */
    public boolean displayBuy(String itemId);
}
