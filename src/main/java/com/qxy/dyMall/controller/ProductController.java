package com.qxy.dyMall.controller;

import com.qxy.dyMall.model.Product;
import com.qxy.dyMall.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/add")
    public ResponseEntity<String> addProduct(@Valid @RequestBody Product product, BindingResult result) {
        // if (result.hasErrors()) {
        //     String errors = result.getFieldErrors().stream()
        //             .map(error -> error.getDefaultMessage())
        //             .collect(Collectors.joining("; "));
        //     return ResponseEntity.badRequest().body("商品添加失败，" + errors);
        // }

        if (product.getName().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("商品添加失败，商品名称不能为空");
        }
        if (product.getPrice() <= 0) {
            return ResponseEntity.badRequest().body("商品添加失败，商品价格必须大于 0");
        }
        if (product.getStock() < 0) {
            return ResponseEntity.badRequest().body("商品添加失败，商品库存不能小于 0");
        }

        productService.addProduct(product);
        return ResponseEntity.ok("商品添加成功");
    }

    @GetMapping("/list")
    public ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        Product product = productService.getProductById(id);
        return product != null ? ResponseEntity.ok(product) : ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateProduct(@PathVariable Long id, @Valid @RequestBody Product product, BindingResult result) {
        // if (result.hasErrors()) {
        //     String errors = result.getFieldErrors().stream()
        //             .map(error -> error.getDefaultMessage())
        //             .collect(Collectors.joining("; "));
        //     return ResponseEntity.badRequest().body("商品更新失败，" + errors);
        // }

        if (product.getName().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("商品更新失败，商品名称不能为空");
        }
        if (product.getPrice() <= 0) {
            return ResponseEntity.badRequest().body("商品更新失败，商品价格必须大于 0");
        }
        if (product.getStock() < 0) {
            return ResponseEntity.badRequest().body("商品更新失败，商品库存不能小于 0");
        }

        boolean updated = productService.updateProduct(id, product);
        return updated ? ResponseEntity.ok("商品更新成功") : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        boolean deleted = productService.deleteProduct(id);
        return deleted ? ResponseEntity.ok("商品删除成功") : ResponseEntity.notFound().build();
    }
}
