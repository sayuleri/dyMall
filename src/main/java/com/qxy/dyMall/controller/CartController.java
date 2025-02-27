package com.qxy.dyMall.controller;

import com.qxy.dyMall.model.CartItem;
import com.qxy.dyMall.model.User;
import com.qxy.dyMall.service.CartService;
import com.qxy.dyMall.service.UserService;
import com.qxy.dyMall.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private UserService userService;  // 获取用户信息

    @Autowired
    private JwtUtil jwtUtil;  // JWT 解析工具类

    // ✅ 添加商品到购物车（userId 从 JWT 解析）
    @PostMapping("/add")
    public ResponseEntity<?> addItem(@RequestHeader("Authorization") String token,
                                     @RequestParam Long productId,
                                     @RequestParam Integer quantity) {
        // 从 JWT 解析 userId
        String username = jwtUtil.extractUsername(token.substring(7));
        User user = userService.findByUsername(username);

        if (user == null) {
            return ResponseEntity.status(401).body("用户不存在");
        }

        cartService.addItem(user.getId(), productId, quantity);
        return ResponseEntity.ok("商品已加入购物车");
    }

    // ✅ 移除购物车中的商品
    @PostMapping("/remove")
    public ResponseEntity<?> removeItem(@RequestHeader("Authorization") String token,
                                        @RequestParam Long productId) {
        String username = jwtUtil.extractUsername(token.substring(7));
        User user = userService.findByUsername(username);

        if (user == null) {
            return ResponseEntity.status(401).body("用户不存在");
        }

        cartService.removeItem(user.getId(), productId);
        return ResponseEntity.ok("商品已移除");
    }

    // ✅ 获取购物车列表
    @GetMapping("/items")
    public ResponseEntity<?> getCartItems(@RequestHeader("Authorization") String token) {
        String username = jwtUtil.extractUsername(token.substring(7));
        User user = userService.findByUsername(username);

        if (user == null) {
            return ResponseEntity.status(401).body("用户不存在");
        }

        List<CartItem> cartItems = cartService.getCartItems(user.getId());
        return ResponseEntity.ok(cartItems);
    }

    // ✅ 清空购物车
    @PostMapping("/clear")
    public ResponseEntity<?> clearCart(@RequestHeader("Authorization") String token) {
        String username = jwtUtil.extractUsername(token.substring(7));
        User user = userService.findByUsername(username);

        if (user == null) {
            return ResponseEntity.status(401).body("用户不存在");
        }

        cartService.clearCart(user.getId());
        return ResponseEntity.ok("购物车已清空");
    }
}
