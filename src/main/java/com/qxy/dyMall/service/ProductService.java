package com.qxy.dyMall.service;

import com.qxy.dyMall.model.Product;
import com.qxy.dyMall.repository.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductMapper productMapper;

    public void addProduct(Product product) {
        productMapper.insertProduct(product);
    }

    public List<Product> getAllProducts() {
        return productMapper.getAllProducts();
    }
}