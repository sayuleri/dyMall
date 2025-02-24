package com.qxy.dyMall.controller;

import com.qxy.dyMall.model.User;
import com.qxy.dyMall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")  // 确保路径是 /user
public class UserController {
    
    @Autowired
    private UserService userService;

    @GetMapping("/getById")  // 确保 GET 请求路径匹配
    public User getUserById(@RequestParam("id") int id) {
        return userService.getUserById(id);
    }
}

