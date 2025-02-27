package com.qxy.dyMall.service;

import com.qxy.dyMall.model.Order;

import java.util.List;

public interface OrderService {
    Order createOrder(Long userId, List<Long> productIds);
    List<Order> getOrders(Long userId);
    Order getOrderDetails(Long orderId);
}
