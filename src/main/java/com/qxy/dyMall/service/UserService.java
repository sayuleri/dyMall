package com.qxy.dyMall.service;

import com.qxy.dyMall.model.User;
import com.qxy.dyMall.repository.UserMapper;
import com.qxy.dyMall.utils.JwtUtil;
import com.qxy.dyMall.controller.LoginRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
// @Transactional
public class UserService {
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    // private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    
    @Autowired
    private UserMapper userMapper;
    
    public void registerUser(String username, String password) {
        if (userMapper.findByUsername(username) != null) {
            throw new RuntimeException("用户名已存在");
        }
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        userMapper.insertUser(user);
    }

    public String loginUser(String username, String password) {
        User user = userMapper.findByUsername(username);
        System.out.println("输入密码: " + LoginRequest.getPassword());
        System.out.println("数据库密码: " + user.getPassword());
        System.out.println("匹配结果: " + passwordEncoder.matches(LoginRequest.getPassword(), user.getPassword()));

        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            return JwtUtil.generateToken(username); // 生成 JWT 令牌
        }
        return null;
    }
    public User getUserById(Long id) {
        return userMapper.findUserById(id);
    }

    public User createUser(User user) {
        saveUser(user);
        return user;
        // TODO Auto-generated method stub
        //throw new UnsupportedOperationException("Unimplemented method 'createUser'");
    }
    
    @Transactional
    public void saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userMapper.insertUser(user);
    }

    @Transactional
    public void updateUser(Long id, User user) {
        User existingUser = userMapper.findUserById(id);
        if (existingUser == null) {
            throw new RuntimeException("User not found");
        }

        existingUser.setUsername(user.getUsername());
        existingUser.setPassword(user.getPassword()); // 这里可以加上密码加密
        existingUser.setEmail(user.getEmail());

        userMapper.updateUser(existingUser);
    }

    @Transactional
    public void deleteUser(Long id) {
        User existingUser = userMapper.findUserById(id);
        if (existingUser == null) {
            throw new RuntimeException("User not found");
        } else {userMapper.deleteUser(id); }
    }

    @Transactional
    public User findByUsername(Object username) {
        User existingUser = userMapper.findUserByName(username);
        if (existingUser == null) {
            throw new RuntimeException("User not found");
        }
                return null;
    }
}