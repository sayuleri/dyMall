package com.qxy.dyMall.controller;

import com.qxy.dyMall.model.Order;
import com.qxy.dyMall.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/create")
    public ResponseEntity<Order> createOrder(
            @RequestHeader("Authorization") String token,
            @RequestBody List<Long> productIds) {
        // 假设从 token 解析出 userId
        Long userId = getUserIdFromToken(token);
        Order order = orderService.createOrder(userId, productIds);
        return ResponseEntity.ok(order);
    }

    @GetMapping("/list")
    public ResponseEntity<List<Order>> getOrders(@RequestHeader("Authorization") String token) {
        Long userId = getUserIdFromToken(token);
        return ResponseEntity.ok(orderService.getOrders(userId));
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<Order> getOrderDetails(@PathVariable Long orderId) {
        return ResponseEntity.ok(orderService.getOrderDetails(orderId));
    }

    // 解析 token，获取用户 ID（需修改为你的 JWT 解析逻辑）
    private Long getUserIdFromToken(String token) {
        return 1L; // 示例代码，需要替换为实际的 token 解析逻辑
    }
}
