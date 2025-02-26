package com.qxy.dyMall.repository;

import com.qxy.dyMall.model.Product;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ProductMapper {

    @Insert("INSERT INTO products(name, description, price, stock) " +
            "SELECT #{name}, #{description}, #{price}, #{stock} FROM DUAL " +
            "WHERE #{price} >= 0 AND #{stock} >= 0")
    void insertProduct(Product product);

    @Select("SELECT * FROM products WHERE id = #{id}")
    Product getProductById(Long id);

    @Select("SELECT * FROM products")
    List<Product> getAllProducts();

    @Update("UPDATE products SET name = #{name}, description = #{description}, price = #{price}, stock = #{stock} WHERE id = #{id}")
    void updateProduct(Product product);

    @Delete("DELETE FROM products WHERE id = #{id}")
    void deleteProduct(Long id);

    @Update("ALTER TABLE products AUTO_INCREMENT = 1")
    void resetAutoIncrement();
}
