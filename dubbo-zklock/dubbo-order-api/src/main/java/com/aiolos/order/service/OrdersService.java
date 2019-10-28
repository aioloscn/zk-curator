package com.aiolos.order.service;

import com.aiolos.order.pojo.Orders;

/**
 * @author Aiolos
 * @date 2019-10-24 14:53
 */
public interface OrdersService {

    /**
     * @Description: 根据订单id查询订单
     */
    public Orders getOrder(String orderId);

    /**
     * @Description: 下订单
     */
    public boolean createOrder(String itemId);
}
