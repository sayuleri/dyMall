package com.qxy.dyMall.service.impl;

import com.qxy.dyMall.mapper.CartMapper;
import com.qxy.dyMall.mapper.OrderItemMapper;
import com.qxy.dyMall.mapper.OrderMapper;
import com.qxy.dyMall.model.Order;
import com.qxy.dyMall.model.OrderItem;
import com.qxy.dyMall.model.CartItem;
import com.qxy.dyMall.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderItemMapper orderItemMapper;

    @Autowired
    private CartMapper cartMapper;

    @Override
    @Transactional
    public Order createOrder(Long userId, List<Long> productIds) {
        // 获取购物车中选中的商品
        List<CartItem> cartItems = cartMapper.getSelectedCartItems(userId, productIds);
        if (cartItems.isEmpty()) {
            throw new IllegalArgumentException("购物车中没有选中的商品");
        }

        // 计算订单总价
        BigDecimal totalPrice = BigDecimal.ZERO;
        List<OrderItem> orderItems = new ArrayList<>();

        for (CartItem item : cartItems) {
            OrderItem orderItem = new OrderItem();
            orderItem.setProductId(item.getProductId());
            orderItem.setQuantity(item.getQuantity());

            // 处理可能的空价格情况
            BigDecimal price = item.getPrice() != null ? item.getPrice() : BigDecimal.ZERO;
            orderItem.setPrice(price);

            totalPrice = totalPrice.add(price.multiply(BigDecimal.valueOf(item.getQuantity())));

            orderItems.add(orderItem);
        }

        // 创建订单
        Order order = new Order();
        order.setUserId(userId);
        order.setTotalPrice(totalPrice);
        order.setStatus(0); // 0 代表待支付
        orderMapper.insertOrder(order);

        // 关联订单商品
        for (OrderItem orderItem : orderItems) {
            orderItem.setOrderId(order.getId());
            orderItemMapper.insertOrderItem(orderItem);
        }

        // 删除购物车中已结算的商品
        cartMapper.deleteCartItems(userId, productIds);

        return order;
    }

    @Override
    public List<Order> getOrders(Long userId) {
        return orderMapper.getOrdersByUserId(userId);
    }

    @Override
    public Order getOrderDetails(Long orderId) {
        return orderMapper.getOrderDetails(orderId);
    }
}
