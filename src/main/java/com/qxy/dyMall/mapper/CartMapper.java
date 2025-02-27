package com.qxy.dyMall.mapper;

import com.qxy.dyMall.model.CartItem;
import org.apache.ibatis.annotations.*;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface CartMapper {

    @Insert("INSERT INTO cart (user_id, product_id, quantity, update_time) VALUES (#{userId}, #{productId}, #{quantity}, #{updateTime}) " +
            "ON DUPLICATE KEY UPDATE quantity = #{quantity}, update_time = #{updateTime}")
    void insertCartItem(Long userId, Long productId, Integer quantity, LocalDateTime updateTime);

    @Delete("DELETE FROM cart WHERE user_id = #{userId} AND product_id = #{productId}")
    void removeCartItem(Long userId, Long productId);

    @Select("SELECT * FROM cart WHERE user_id = #{userId}")
    List<CartItem> findCartByUserId(Long userId);

    @Delete("DELETE FROM cart WHERE user_id = #{userId}")
    void clearCartByUserId(Long userId);
}
