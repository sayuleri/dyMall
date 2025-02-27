package com.qxy.dyMall.mapper;

import com.qxy.dyMall.model.Order;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface OrderMapper {

    @Insert("INSERT INTO orders (user_id, total_price, status, created_at) VALUES (#{userId}, #{totalPrice}, #{status}, NOW())")
    void insertOrder(Order order);

    @Select("SELECT * FROM orders WHERE user_id = #{userId}")
    List<Order> getOrdersByUserId(Long userId);

    @Select("SELECT * FROM orders WHERE id = #{orderId}")
    Order getOrderDetails(Long orderId);
}
