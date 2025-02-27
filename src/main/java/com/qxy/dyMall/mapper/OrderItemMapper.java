package com.qxy.dyMall.mapper;

import com.qxy.dyMall.model.OrderItem;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderItemMapper {

    @Insert("INSERT INTO order_items (order_id, product_id, quantity, price) VALUES (#{orderId}, #{productId}, #{quantity}, #{price})")
    void insertOrderItem(OrderItem orderItem);
}
