package com.aiolos.mapper;

import com.aiolos.pojo.Items;
import com.aiolos.pojo.ItemsExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Aiolos
 * @date 2019-10-24 14:38
 */
public interface ItemsMapper {

    int countByExample(ItemsExample example);

    int deleteByExample(ItemsExample example);

    int deleteByPrimaryKey(String id);

    int insert(Items record);

    int insertSelective(Items record);

    List<Items> selectByExample(ItemsExample example);

    Items selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") Items record, @Param("example") ItemsExample example);

    int updateByExample(@Param("record") Items record, @Param("example") ItemsExample example);

    int updateByPrimaryKeySelective(Items record);

    int updateByPrimaryKey(Items record);

    int reduceCounts(Items record);
}
