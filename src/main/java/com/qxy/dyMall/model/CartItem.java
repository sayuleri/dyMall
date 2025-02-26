package com.qxy.dyMall.model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class CartItem implements Serializable {
    private Long productId;
    private Integer quantity;
    private LocalDateTime updateTime;

    public CartItem() {}

    public CartItem(Long productId, Integer quantity) {
        this.productId = productId;
        this.quantity = quantity;
        this.updateTime = LocalDateTime.now();
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
        this.updateTime = LocalDateTime.now();
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    @Override
    public String toString() {
        return "CartItem{" +
                "productId=" + productId +
                ", quantity=" + quantity +
                ", updateTime=" + updateTime +
                '}';
    }
}
