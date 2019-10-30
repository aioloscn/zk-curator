package com.aiolos.web.controller;

import com.aiolos.common.utils.CustomizeJSONResult;
import com.aiolos.curator.utils.ZKCurator;
import com.aiolos.item.pojo.Items;
import com.aiolos.item.service.ItemsService;
import com.aiolos.order.pojo.Orders;
import com.aiolos.order.service.OrdersService;
import com.aiolos.web.service.ClusterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Aiolos
 * @date 2019-10-28 22:44
 */
@Controller
public class PayController {

    final static Logger log = LoggerFactory.getLogger(PayController.class);

    @Autowired
    private ItemsService itemsService;

    @Autowired
    private OrdersService ordersService;

    @Autowired
    private ClusterService clusterService;

    @Autowired
    private ZKCurator zkCurator;

    @RequestMapping("/test")
    @ResponseBody
    public CustomizeJSONResult test() {

        return CustomizeJSONResult.ok("test");
    }

    @RequestMapping("/item")
    @ResponseBody
    public CustomizeJSONResult getItemById(String id) {

        Items items = itemsService.getItem(id);

        return CustomizeJSONResult.ok(items);
    }

    @RequestMapping("/order")
    @ResponseBody
    public CustomizeJSONResult getOrderById(String id) {

        Orders orders = ordersService.getOrder(id);

        return CustomizeJSONResult.ok(orders);
    }

    @RequestMapping("/buy")
    @ResponseBody
    public CustomizeJSONResult buy(String itemId) {
        boolean result = clusterService.displayBuy(itemId);
        return CustomizeJSONResult.ok(result ? "订单创建成功..." : "订单创建失败...");
    }

    /**
     * @Description: 模拟集群下的数据不一致
     */
    @RequestMapping("/buy2")
    @ResponseBody
    public CustomizeJSONResult buy2(String itemId) {
        boolean result = clusterService.displayBuy(itemId);
        return CustomizeJSONResult.ok(result ? "订单创建成功..." : "订单创建失败...");
    }

    /**
     * @Description: 判断zk是否连接
     */
    @RequestMapping("/isZKAlive")
    @ResponseBody
    public CustomizeJSONResult isZKAlive() {
        boolean isAlive = zkCurator.isZKAlive();
        String result = isAlive ? "连接" : "断开";
        return CustomizeJSONResult.ok(result);
    }
}
