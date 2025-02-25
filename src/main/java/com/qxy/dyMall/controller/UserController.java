package com.qxy.dyMall.controller;

import com.qxy.dyMall.model.User;
import com.qxy.dyMall.service.UserService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import com.qxy.dyMall.controller.LoginRequest;


@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public String register(@RequestBody Map<String, String> request) {
        userService.registerUser(request.get("username"), request.get("password"));
        return "注册成功";
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        User user = userService.findByUsername(loginRequest.getUsername());
        if (user == null || !passwordEncoder.matches((CharSequence) loginRequest.getPassword(), user.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("用户名或密码错误");
        }

        @SuppressWarnings("deprecation")
        String token = Jwts.builder()
            .setSubject(user.getUsername())
            .setIssuedAt(new Date(0))
            .setExpiration(new Date(System.currentTimeMillis() + 7 * 24 * 60 * 60 * 1000)) // 7 天有效期
            .signWith(SignatureAlgorithm.HS256, "your-256-bit-secret".getBytes(StandardCharsets.UTF_8))
            .compact();

        return ResponseEntity.ok(Collections.singletonMap("token", token));
    }


    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        return user != null ? ResponseEntity.ok(user) : ResponseEntity.notFound().build();
    }

    // @PostMapping
    // public ResponseEntity<User> createUser(@RequestBody User user) {//创建用户
    //     User savedUser = userService.createUser(user);
    //     return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    // }

    // @PostMapping
    // public ResponseEntity<String> createUser(@RequestBody User user) {//创建用户
    //     System.out.println("Received user: " + user.getUsername());
    //     return ResponseEntity.ok("User created: " + user.getUsername());
    // }

    // @PostMapping("/users")
    // public ResponseEntity<String> createUser(@RequestBody User user) {
    //     userService.saveUser(user);
    //     return ResponseEntity.ok("User created successfully");
    // }

    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody User user) {
        userService.saveUser(user);
        return ResponseEntity.ok("User created: " + user.getUsername());
    }

    @PutMapping("/{id}")  // 确保映射正确
    public ResponseEntity<String> updateUser(@PathVariable Long id, @RequestBody User user) {
        try {
            userService.updateUser(id, user);
            return ResponseEntity.ok("User updated successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update user");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}