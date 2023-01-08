package com.ccg.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ccg.reggie.entity.Orders;
import org.apache.ibatis.annotations.Mapper;

import java.util.stream.BaseStream;
@Mapper
public interface OrdersMapper extends BaseMapper<Orders> {
}
