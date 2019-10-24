package com.aiolos.web.service;

/**
 * @author Aiolos
 * @date 2019-10-24 15:01
 */
public interface BuyService {

    /**
     * @Description: 购买商品
     */
    public void doBuyItem(String itemId);

    public boolean displayBuy(String itemId);
}
