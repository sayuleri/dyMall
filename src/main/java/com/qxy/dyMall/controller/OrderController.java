package com.qxy.dyMall.controller;

import com.qxy.dyMall.dto.CancelOrderRequest;
import com.qxy.dyMall.dto.OrderUpdateRequest;
import com.qxy.dyMall.model.Order;
import com.qxy.dyMall.service.OrderService;
import com.qxy.dyMall.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/order") // 确保路径与 Postman 一致
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/create")
    public ResponseEntity<Order> createOrder(
            @RequestHeader("Authorization") String token,
            @RequestBody List<Long> productIds) {
        Long userId = jwtUtil.getUserIdFromToken(token);
        Order order = orderService.createOrder(userId, productIds);
        return ResponseEntity.ok(order);
    }

    @GetMapping("/list")
    public ResponseEntity<List<Order>> getOrders(@RequestHeader("Authorization") String token) {
        Long userId = jwtUtil.getUserIdFromToken(token);
        return ResponseEntity.ok(orderService.getOrders(userId));
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<Order> getOrderDetails(@PathVariable Long orderId) {
        return ResponseEntity.ok(orderService.getOrderDetails(orderId));
    }

    // ✅ **新增：修改订单**
    @PutMapping("/update")
    public ResponseEntity<Order> updateOrder(
            @RequestHeader("Authorization") String token,
            @RequestBody OrderUpdateRequest updateRequest) {
        Long userId = jwtUtil.getUserIdFromToken(token);
        Order updatedOrder = orderService.updateOrder(userId, updateRequest);
        return ResponseEntity.ok(updatedOrder);
    }

    // ✅ **新增：取消订单**
    @DeleteMapping("/cancel")
    public ResponseEntity<String> cancelOrder(
            @RequestHeader("Authorization") String token,
            @RequestBody Map<String, Long> requestBody) {
        Long userId = jwtUtil.getUserIdFromToken(token);
        Long orderId = requestBody.get("orderId");

        orderService.cancelOrder(userId, orderId);

        return ResponseEntity.ok("订单取消成功");
    }

}
