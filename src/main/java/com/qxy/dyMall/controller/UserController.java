package com.qxy.dyMall.controller;

import com.qxy.dyMall.model.User;
import com.qxy.dyMall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public String register(@RequestBody Map<String, String> request) {
        userService.registerUser(request.get("username"), request.get("password"));
        return "注册成功";
    }

    @PostMapping("/login")
    public String login(@RequestBody Map<String, String> request) {
        boolean success = userService.loginUser(request.get("username"), request.get("password"));
        return success ? "登录成功" : "用户名或密码错误";
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

    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody User user) {//创建用户
        System.out.println("Received user: " + user.getUsername());
        return ResponseEntity.ok("User created: " + user.getUsername());
    }
}