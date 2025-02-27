package com.qxy.dyMall.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.qxy.dyMall.model.CartItem;
import com.qxy.dyMall.mapper.CartMapper;
import com.qxy.dyMall.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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

    @Transactional
    @Override
    public void addItem(Long userId, Long productId, Integer quantity) {
        String cartKey = CART_KEY_PREFIX + userId;
        String productKey = String.valueOf(productId);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        // 反序列化 Redis 存储的数据
        Object redisData = redisTemplate.opsForHash().get(cartKey, productKey);
        CartItem item = (redisData != null) ? objectMapper.convertValue(redisData, CartItem.class) : null;

        if (item == null) {
            item = new CartItem(productId, quantity, LocalDateTime.now());
        } else {
            item.setQuantity(item.getQuantity() + quantity);
            item.setUpdateTime(LocalDateTime.now());
        }

        redisTemplate.opsForHash().put(cartKey, productKey, item);
        redisTemplate.expire(cartKey, 7, TimeUnit.DAYS);

        cartMapper.insertCartItem(userId, productId, item.getQuantity(), item.getUpdateTime());
    }

    @Transactional
    @Override
    public void removeItem(Long userId, Long productId) {
        String cartKey = CART_KEY_PREFIX + userId;
        redisTemplate.opsForHash().delete(cartKey, String.valueOf(productId));
        cartMapper.removeCartItem(userId, productId);
    }

    @Override
    public List<CartItem> getCartItems(Long userId) {
        String cartKey = CART_KEY_PREFIX + userId;
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        List<Object> rawCartItems = redisTemplate.opsForHash().values(cartKey);
        List<CartItem> cartItems = rawCartItems.stream()
                .map(obj -> objectMapper.convertValue(obj, CartItem.class))
                .collect(Collectors.toList());

        if (cartItems.isEmpty()) {
            cartItems = cartMapper.findCartByUserId(userId);
            for (CartItem item : cartItems) {
                redisTemplate.opsForHash().put(cartKey, String.valueOf(item.getProductId()), item);
            }
            redisTemplate.expire(cartKey, 7, TimeUnit.DAYS);
        }

        return cartItems;
    }

    @Transactional
    @Override
    public void clearCart(Long userId) {
        String cartKey = CART_KEY_PREFIX + userId;
        redisTemplate.delete(cartKey);
        cartMapper.clearCartByUserId(userId);
    }
}
