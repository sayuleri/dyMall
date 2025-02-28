package com.qxy.dyMall.mapper;

import com.qxy.dyMall.model.Order;
import com.qxy.dyMall.model.OrderStatus;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.EnumOrdinalTypeHandler;

import java.math.BigDecimal;
import java.util.List;

@Mapper
public interface OrderMapper {

    // @Insert("INSERT INTO orders (user_id, total_price, status, created_at) VALUES (#{userId}, #{totalPrice}, #{status}, NOW())")
    // @Options(useGeneratedKeys = true, keyProperty = "id")
    // void insertOrder(Order order);

    @Insert("INSERT INTO orders (user_id, total_price, status, created_at) VALUES (#{userId}, #{totalPrice}, #{status.code}, NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insertOrder(Order order);

    @Select("SELECT created_at FROM orders WHERE id = #{id}")
    @Results({
        @Result(column = "created_at", property = "createdAt")
    })
    Order getCreatedAt(Long id);

    @Select("SELECT * FROM orders WHERE user_id = #{userId}")
    List<Order> getOrdersByUserId(Long userId);

    // @Select("SELECT * FROM orders WHERE id = #{orderId}")
    // @Results({
    //     @Result(column = "status", property = "status", javaType = OrderStatus.class, typeHandler = EnumOrdinalTypeHandler.class)
    // })
    // Order getOrderById(Long orderId);

    // @Select("SELECT * FROM orders WHERE id = #{orderId}")
    // @Results({
    //     @Result(column = "status", property = "status", javaType = OrderStatus.class, typeHandler = org.apache.ibatis.type.EnumOrdinalTypeHandler.class),
    //     @Result(column = "created_at", property = "createdAt")
    // })
    // Order getOrderById(Long orderId);

    // @Select("SELECT id, user_id, total_price, CAST(status AS SIGNED) AS status, created_at FROM orders WHERE id = #{orderId}")
    // @Results({
    //     @Result(column = "status", property = "status", javaType = OrderStatus.class, typeHandler = org.apache.ibatis.type.EnumOrdinalTypeHandler.class),
    //     @Result(column = "created_at", property = "createdAt", javaType = java.time.LocalDateTime.class)
    // })
    // Order getOrderById(Long orderId);

    @Select("SELECT id, user_id, total_price, CAST(status AS SIGNED) AS status, created_at FROM orders WHERE id = #{orderId}")
    @Results({
        @Result(column = "status", property = "status", javaType = int.class),
        @Result(column = "created_at", property = "createdAt")
    })
    Order getOrderById(Long orderId);


    @Select("SELECT * FROM orders WHERE id = #{orderId}")
    Order getOrderDetails(Long orderId);

    // @Update("UPDATE orders SET total_price = #{totalPrice} WHERE id = #{id}")
    // void updateOrder(Order order);

    @Update("UPDATE orders SET status = #{status.code} WHERE id = #{id}")
    void updateOrder(Order order);


    // @Update("UPDATE orders SET status = 1 WHERE id = #{orderId}")
    // void cancelOrder(Long orderId);

    @Update("UPDATE orders SET total_price = #{totalPrice} WHERE id = #{orderId}")
    void updateOrderTotalPrice(@Param("orderId") Long orderId, @Param("totalPrice") BigDecimal totalPrice);

    @Delete("DELETE FROM orders WHERE id = #{orderId}")
    void deleteOrder(Long orderId);

}
