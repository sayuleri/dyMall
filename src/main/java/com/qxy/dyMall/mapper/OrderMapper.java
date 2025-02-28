package com.qxy.dyMall.mapper;

import com.qxy.dyMall.model.Order;
import org.apache.ibatis.annotations.*;

import java.math.BigDecimal;
import java.util.List;

@Mapper
public interface OrderMapper {

    @Insert("INSERT INTO orders (user_id, total_price, status, created_at) VALUES (#{userId}, #{totalPrice}, #{status}, NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insertOrder(Order order);

    @Select("SELECT * FROM orders WHERE user_id = #{userId}")
    List<Order> getOrdersByUserId(Long userId);

    @Select("SELECT * FROM orders WHERE id = #{orderId}")
    Order getOrderById(Long orderId);

    @Select("SELECT * FROM orders WHERE id = #{orderId}")
    Order getOrderDetails(Long orderId);

    @Update("UPDATE orders SET total_price = #{totalPrice} WHERE id = #{id}")
    void updateOrder(Order order);

    // @Update("UPDATE orders SET status = 1 WHERE id = #{orderId}")
    // void cancelOrder(Long orderId);

    @Update("UPDATE orders SET total_price = #{totalPrice} WHERE id = #{orderId}")
    void updateOrderTotalPrice(@Param("orderId") Long orderId, @Param("totalPrice") BigDecimal totalPrice);

    @Delete("DELETE FROM orders WHERE id = #{orderId}")
    void deleteOrder(Long orderId);

}
