package com.qxy.dyMall.service;

import com.qxy.dyMall.model.CartItem;

import java.util.List;

public interface CartService {
    void addItem(Long userId, Long productId, Integer quantity);
    void removeItem(Long userId, Long productId);
    List<CartItem> getCartItems(Long userId);
    void clearCart(Long userId);

}