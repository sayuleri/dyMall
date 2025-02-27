package com.qxy.dyMall.controller;

import com.qxy.dyMall.model.CartItem;
import com.qxy.dyMall.service.CartService;
import com.qxy.dyMall.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private CartService cartService;

    @PostMapping("/add")
    public ResponseEntity<?> addItem(@RequestHeader("Authorization") String token,
                                     @RequestParam Long productId,
                                     @RequestParam Integer quantity) {
        String username = jwtUtil.extractUsername(token.substring(7));
        Long userId = jwtUtil.getUserIdFromUsername(username);

        cartService.addItem(userId, productId, quantity);
        return ResponseEntity.ok("商品已加入购物车");
    }

    @GetMapping("/list")
    public ResponseEntity<List<CartItem>> getCartItems(@RequestHeader("Authorization") String token) {
        String username = jwtUtil.extractUsername(token.substring(7));
        Long userId = jwtUtil.getUserIdFromUsername(username);

        return ResponseEntity.ok(cartService.getCartItems(userId));
    }

    @DeleteMapping("/clear")
    public ResponseEntity<?> clearCart(@RequestHeader("Authorization") String token) {
        String username = jwtUtil.extractUsername(token.substring(7));
        Long userId = jwtUtil.getUserIdFromUsername(username);

        cartService.clearCart(userId);
        return ResponseEntity.ok("购物车已清空");
    }
}
