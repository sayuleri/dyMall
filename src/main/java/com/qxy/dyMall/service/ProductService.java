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
        productMapper.resetAutoIncrement();
    }

    public List<Product> getAllProducts() {
        return productMapper.getAllProducts();
    }

    public Product getProductById(Long id) {
        return productMapper.getProductById(id);
    }

    public boolean updateProduct(Long id, Product product) {
        Product existingProduct = productMapper.getProductById(id);
        if (existingProduct == null) {
            return false;
        }
        product.setId(id);
        productMapper.updateProduct(product);
        return true;
    }

    public boolean deleteProduct(Long id) {
        Product product = productMapper.getProductById(id);
        if (product == null) {
            return false;
        }
        productMapper.deleteProduct(id);
        productMapper.resetAutoIncrement();
        return true;
    }
}
