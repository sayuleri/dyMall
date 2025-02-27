package com.qxy.dyMall.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class CartItem implements Serializable {
    private Long id;
    private Long userId;
    private Long productId;
    private int quantity;
    private BigDecimal price;
    private LocalDateTime updateTime;

    // ✅ 无参构造函数（Jackson 反序列化需要）
    public CartItem() {
    }

    // ✅ 构造函数（匹配 addItem 方法）
    public CartItem(Long productId, Integer quantity, LocalDateTime updateTime) {
        this.productId = productId;
        this.quantity = quantity;
        this.updateTime = updateTime;
        this.price = BigDecimal.ZERO; // 避免空指针
    }

    // ✅ Getter 和 Setter 方法
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "CartItem{" +
                "productId=" + productId +
                ", quantity=" + quantity +
                ", price=" + price +
                ", updateTime=" + updateTime +
                '}';
    }
}
