package com.qxy.dyMall.mapper;

import com.qxy.dyMall.model.CartItem;
import org.apache.ibatis.annotations.*;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface CartMapper {

    // ✅ 确保用户有一个购物车
    @Insert("INSERT INTO cart (user_id, created_at) VALUES (#{userId}, NOW()) " +
            "ON DUPLICATE KEY UPDATE created_at = NOW()")
    void ensureCartExists(@Param("userId") Long userId);

    // ✅ 插入购物车商品，已存在则更新数量
    @Insert("INSERT INTO cart_items (cart_id, product_id, quantity, update_time) " +
            "VALUES ((SELECT id FROM cart WHERE user_id = #{userId}), #{productId}, #{quantity}, NOW()) " +
            "ON DUPLICATE KEY UPDATE quantity = quantity + VALUES(quantity), update_time = NOW()")
    void insertOrUpdateCartItem(@Param("userId") Long userId,
                                @Param("productId") Long productId,
                                @Param("quantity") Integer quantity);

    // ✅ 删除购物车中的商品
    @Delete("DELETE FROM cart_items WHERE cart_id = (SELECT id FROM cart WHERE user_id = #{userId}) AND product_id = #{productId}")
    void removeCartItem(@Param("userId") Long userId, @Param("productId") Long productId);

    // ✅ 获取用户购物车的所有商品
    @Select("SELECT ci.* FROM cart_items ci INNER JOIN cart c ON ci.cart_id = c.id WHERE c.user_id = #{userId}")
    List<CartItem> findCartByUserId(@Param("userId") Long userId);

    // ✅ 清空购物车
    @Delete("DELETE FROM cart_items WHERE cart_id = (SELECT id FROM cart WHERE user_id = #{userId})")
    void clearCartByUserId(@Param("userId") Long userId);

    @Select("SELECT * FROM cart WHERE user_id = #{userId} AND product_id IN (${productIds})")
    List<CartItem> getSelectedCartItems(Long userId, List<Long> productIds);

    @Delete("DELETE FROM cart WHERE user_id = #{userId} AND product_id IN (${productIds})")
    void deleteCartItems(Long userId, List<Long> productIds);
}
