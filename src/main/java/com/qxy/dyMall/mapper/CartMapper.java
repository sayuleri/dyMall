package com.qxy.dyMall.mapper;

import com.qxy.dyMall.model.CartItem;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CartMapper {

    @Select("SELECT * FROM cart WHERE user_id = #{userId}")
    List<CartItem> findCartByUserId(@Param("userId") Long userId);

    @Insert("INSERT INTO cart (user_id, product_id, quantity, update_time) VALUES (#{userId}, #{productId}, #{quantity}, NOW()) " +
            "ON DUPLICATE KEY UPDATE quantity = quantity + #{quantity}, update_time = NOW()")
    void addOrUpdateCartItem(@Param("userId") Long userId, @Param("productId") Long productId, @Param("quantity") Integer quantity);

    @Delete("DELETE FROM cart WHERE user_id = #{userId} AND product_id = #{productId}")
    void removeCartItem(@Param("userId") Long userId, @Param("productId") Long productId);

    @Delete("DELETE FROM cart WHERE user_id = #{userId}")
    void clearCartByUserId(@Param("userId") Long userId);

}

