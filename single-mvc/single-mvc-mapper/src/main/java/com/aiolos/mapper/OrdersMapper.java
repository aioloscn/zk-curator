package com.aiolos.mapper;

import com.aiolos.pojo.Orders;
import com.aiolos.pojo.OrdersExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Aiolos
 * @date 2019-10-24 14:46
 */
public interface OrdersMapper {

    int countByExample(OrdersExample example);

    int deleteByExample(OrdersExample example);

    int deleteByPrimaryKey(String id);

    int insert(Orders record);

    int insertSelective(Orders record);

    List<Orders> selectByExample(OrdersExample example);

    Orders selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") Orders record, @Param("example") OrdersExample example);

    int updateByExample(@Param("record") Orders record, @Param("example") OrdersExample example);

    int updateByPrimaryKeySelective(Orders record);

    int updateByPrimaryKey(Orders record);
}
