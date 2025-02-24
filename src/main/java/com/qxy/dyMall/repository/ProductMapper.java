package com.qxy.dyMall.repository;

import com.qxy.dyMall.model.Product;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ProductMapper {
    @Insert("INSERT INTO products(name, price, stock) VALUES(#{name}, #{price}, #{stock})")
    void insertProduct(Product product);// 添加商品

    @Select("SELECT * FROM products")
    List<Product> getAllProducts();// 获取所有商品
}