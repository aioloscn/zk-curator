package com.aiolos.order.service.impl;

import com.aiolos.mapper.OrdersMapper;
import com.aiolos.order.service.OrdersService;
import com.aiolos.pojo.Orders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * @author Aiolos
 * @date 2019-10-24 14:53
 */
@Service("ordersService")
public class OrdersServiceImpl implements OrdersService {

    final static Logger log = LoggerFactory.getLogger(OrdersServiceImpl.class);

    @Autowired
    private OrdersMapper ordersMapper;

    @Override
    public Orders getOrder(String orderId) {
        return ordersMapper.selectByPrimaryKey(orderId);
    }

    @Override
    public boolean createOrder(String itemId) {

        // 创建订单
        String oid = UUID.randomUUID().toString().replaceAll("-", "");
        Orders o = new Orders();
        o.setId(oid);
        o.setOrderNum(oid);
        o.setItemId(itemId);
        ordersMapper.insert(o);

        log.info("订单创建成功");

        return true;
    }
}
