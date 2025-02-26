package com.qxy.dyMall.service.impl;

import com.qxy.dyMall.model.CartItem;
import com.qxy.dyMall.mapper.CartMapper;
import com.qxy.dyMall.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private CartMapper cartMapper;

    private static final String CART_KEY_PREFIX = "cart:";

    @Override
    public void addItem(Long userId, Long productId, Integer quantity) {
        String cartKey = CART_KEY_PREFIX + userId;  // 确保 Redis Key 是 String
        String productKey = String.valueOf(productId);  // 确保 Hash Key 也是 String

        // 获取购物车中的商品
        CartItem item = (CartItem) redisTemplate.opsForHash().get(cartKey, productKey);
        
        if (item == null) {
            item = new CartItem(productId, quantity);
        } else {
            item.setQuantity(item.getQuantity() + quantity);
        }
        
        redisTemplate.opsForHash().put(cartKey, productKey, item);
    }

    @Override
    public void removeItem(Long userId, Long productId) {
        String cartKey = CART_KEY_PREFIX + userId;
        redisTemplate.opsForHash().delete(cartKey, productId);
        cartMapper.removeCartItem(userId, productId);
    }

    @Override
    public List<CartItem> getCartItems(Long userId) {
        String cartKey = CART_KEY_PREFIX + userId;

        // 获取 Redis 中的数据
        List<Object> rawCartItems = redisTemplate.opsForHash().values(cartKey);
        List<CartItem> cartItems = rawCartItems.stream()
                .map(obj -> (CartItem) obj)
                .collect(Collectors.toList());

        if (cartItems == null || cartItems.isEmpty()) {
            cartItems = cartMapper.findCartByUserId(userId);
            for (CartItem item : cartItems) {
                redisTemplate.opsForHash().put(cartKey, item.getProductId(), item);
            }
            redisTemplate.expire(cartKey, 7, TimeUnit.DAYS);
        }

        return cartItems;
    }

    @Override
    public void clearCart(Long userId) {
        String cartKey = CART_KEY_PREFIX + userId;
        redisTemplate.delete(cartKey); // 删除 Redis 购物车数据
        cartMapper.clearCartByUserId(userId); // 删除数据库中的购物车记录
    }

}