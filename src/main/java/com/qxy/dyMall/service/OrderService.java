package com.qxy.dyMall.service;

import com.qxy.dyMall.dto.OrderUpdateRequest;
import com.qxy.dyMall.model.Order;

import java.util.List;

public interface OrderService {
    Order createOrder(Long userId, List<Long> productIds);
    List<Order> getOrders(Long userId);
    Order getOrderDetails(Long orderId);
    Order updateOrder(Long userId, OrderUpdateRequest request); // 新增订单修改方法
    void cancelOrder(Long userId, Long orderId); // 新增订单取消方法
    Order confirmOrder(Long userId, Long orderId); // 新增订单结算方法
    boolean payOrder(Long userId, Long orderId); // 新增支付方法
}
