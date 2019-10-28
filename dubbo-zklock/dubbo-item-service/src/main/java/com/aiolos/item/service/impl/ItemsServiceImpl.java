package com.aiolos.item.service.impl;

import com.aiolos.item.mapper.ItemsMapper;
import com.aiolos.item.pojo.Items;
import com.aiolos.item.service.ItemsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Aiolos
 * @date 2019-10-24 14:50
 */
@Service("itemsService")
public class ItemsServiceImpl implements ItemsService {

    @Autowired
    private ItemsMapper itemsMapper;

    @Override
    public Items getItem(String itemId) {
        return itemsMapper.selectByPrimaryKey(itemId);
    }

    @Override
    public int getItemCounts(String itemId) {
        Items item = itemsMapper.selectByPrimaryKey(itemId);
        return item.getCounts();
    }

    @Override
    public void displayReduceCounts(String itemId, int buyCounts) {

//		int a  = 1 / 0;

        Items reduceItem = new Items();
        reduceItem.setId(itemId);
        reduceItem.setBuyCounts(buyCounts);
        itemsMapper.reduceCounts(reduceItem);
    }
}
