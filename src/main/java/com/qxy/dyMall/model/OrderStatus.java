package com.qxy.dyMall.model;

public enum OrderStatus {
    PENDING(0),   // 待支付
    CONFIRMED(2), // 已确认
    PAID(1),      // 已支付
    CANCELED(3);  // 已取消

    private final int code;

    OrderStatus(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static OrderStatus fromCode(int code) {
        for (OrderStatus status : values()) {
            if (status.getCode() == code) {
                return status;
            }
        }
        throw new IllegalArgumentException("无效的订单状态: " + code);
    }
}
