package com.qxy.dyMall.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class CartItem implements Serializable {
    private Long productId;
    private Integer quantity;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    public CartItem(Long productId, Integer quantity, LocalDateTime updateTime) {
        this.productId = productId;
        this.quantity = quantity;
        this.updateTime = updateTime;
    }
}
