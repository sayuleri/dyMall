package com.qxy.dyMall.mapper;

import com.qxy.dyMall.model.OrderItem;

import io.lettuce.core.dynamic.annotation.Param;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderItemMapper {

    @Insert("INSERT INTO order_items (order_id, product_id, quantity, price) VALUES (#{orderId}, #{productId}, #{quantity}, #{price})")
    void insertOrderItem(OrderItem orderItem);

    @Delete("DELETE FROM order_items WHERE order_id = #{orderId}")
    void deleteOrderItems(@Param("orderId") Long orderId);

    // 这里可以添加一个方法来批量插入订单项（可选）
    void batchInsertOrderItems(@Param("orderItems") List<OrderItem> orderItems);
}
