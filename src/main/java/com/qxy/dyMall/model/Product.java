package com.qxy.dyMall.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class Product {
    private Long id;

    @NotBlank(message = "商品名称不能为空")
    private String name;

    private String description;

    @NotNull(message = "价格不能为空")
    @Min(value = 0, message = "价格不能为负数")
    private double price;

    @NotNull(message = "库存不能为空")
    @Min(value = 0, message = "库存不能为负数")
    private int stock;
}
