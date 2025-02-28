package com.qxy.dyMall.service.impl;

import com.qxy.dyMall.dto.OrderUpdateRequest;
import com.qxy.dyMall.mapper.CartMapper;
import com.qxy.dyMall.mapper.OrderItemMapper;
import com.qxy.dyMall.mapper.OrderMapper;
import com.qxy.dyMall.model.Order;
import com.qxy.dyMall.model.OrderItem;
import com.qxy.dyMall.model.CartItem;
import com.qxy.dyMall.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Slf4j // ✅ 添加日志支持
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
        log.debug("用户 {} 请求创建订单，选择的商品 ID: {}", userId, productIds); // ✅ 日志1：记录请求参数

        // 1️⃣ 获取购物车中选中的商品
        List<CartItem> cartItems = cartMapper.getSelectedCartItems(userId, productIds);
        if (cartItems.isEmpty()) {
            log.warn("用户 {} 选择的商品不在购物车中，无法创建订单", userId); // ⚠️ 日志2：购物车为空
            throw new IllegalArgumentException("选择的商品不在购物车中，无法结算");
        }

        // 2️⃣ 检查购物车商品种类是否超过 300
        int cartSize = cartMapper.countCartItems(userId);
        if (cartSize > 300) {
            log.warn("用户 {} 购物车商品种类超过 300，无法结算", userId);
            throw new IllegalArgumentException("购物车商品种类最多为 300");
        }

        // 3️⃣ 计算订单总价
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

        // 4️⃣ 创建订单
        Order order = new Order();
        order.setUserId(userId);
        order.setTotalPrice(totalPrice);
        order.setStatus(0); // 0 代表待支付
        orderMapper.insertOrder(order);
        
        // ✅ 添加日志，检查 order.id 是否正确赋值
        log.debug("订单创建成功，订单 ID: {}", order.getId());
        if (order.getId() == null) {
            throw new IllegalStateException("订单创建失败，order.id 为空");
        }

        // 5️⃣ 关联订单商品
        for (OrderItem orderItem : orderItems) {
            orderItem.setOrderId(order.getId());
            orderItemMapper.insertOrderItem(orderItem);
        }

        // 6️⃣ 删除购物车中已结算的商品
        cartMapper.deleteCartItems(userId, productIds);
        log.info("用户 {} 订单创建成功, 订单ID: {}, 总价: {}", userId, order.getId(), totalPrice); // ✅ 日志3：订单成功创建

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

    @Override
    @Transactional
    public Order updateOrder(Long userId, OrderUpdateRequest request) {
        log.info("用户 {} 请求修改订单 ID: {}", userId, request.getOrderId());

        Order order = orderMapper.getOrderDetails(request.getOrderId());
        if (order == null || !order.getUserId().equals(userId)) {
            throw new IllegalArgumentException("订单不存在或无权限修改");
        }

        BigDecimal totalPrice = BigDecimal.ZERO;
        List<OrderItem> newOrderItems = new ArrayList<>();

        for (int i = 0; i < request.getNewProductIds().size(); i++) {
            OrderItem item = new OrderItem();
            item.setOrderId(order.getId());
            item.setProductId(request.getNewProductIds().get(i));
            item.setQuantity(request.getNewQuantities().get(i));

            BigDecimal price = cartMapper.getProductPrice(item.getProductId());
            item.setPrice(price);
            totalPrice = totalPrice.add(price.multiply(BigDecimal.valueOf(item.getQuantity())));

            newOrderItems.add(item);
        }

        orderItemMapper.deleteOrderItems(order.getId());

        for (OrderItem item : newOrderItems) {
            orderItemMapper.insertOrderItem(item);
        }

        orderMapper.updateOrderTotalPrice(order.getId(), totalPrice);
        log.info("订单 {} 更新成功，新总价: {}", order.getId(), totalPrice);

        return orderMapper.getOrderDetails(order.getId());
    }

    @Override
    public void cancelOrder(Long userId, Long orderId) {
        // 1. 查询订单是否属于当前用户
        Order order = orderMapper.getOrderById(orderId);
        if (order == null || !order.getUserId().equals(userId)) {
            throw new IllegalArgumentException("订单不存在或无权限取消");
        }

        // 2. 检查订单是否已支付（如果有支付状态）
        if (order.getStatus() == 1) { // 假设 1 表示已支付
            throw new IllegalStateException("订单已支付，无法取消");
        }

        // 3. 删除订单商品
        orderItemMapper.deleteOrderItems(orderId);

        // 4. 删除订单
        orderMapper.deleteOrder(orderId);

        log.info("用户 {} 取消了订单 ID: {}", userId, orderId);
    }

}
