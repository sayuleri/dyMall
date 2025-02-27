package com.qxy.dyMall.dto;

import lombok.Data;
import java.util.List;

@Data
public class OrderUpdateRequest {
    private Long orderId;
    private List<Long> newProductIds;
    private List<Integer> newQuantities;
}
