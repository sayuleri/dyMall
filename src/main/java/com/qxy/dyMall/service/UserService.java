package com.qxy.dyMall.service;

import com.qxy.dyMall.model.User;
import com.qxy.dyMall.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;


@Service
public class UserService {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserMapper userRepository;

    // 🔹 注册用户（新增 email 唯一性检查）
    public void registerUser(String username, String password, String email) {
        if (userMapper.findByUsername(username) != null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "用户名已存在");
        }
        if (userMapper.findByEmail(email) != null) { // 🔥 关键：防止 Email 重复
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email 已存在");
        }
        
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setEmail(email);
        userMapper.insertUser(user);
    }

    // 🔹 登录用户
    public boolean loginUser(String username, String password) {
        User user = userMapper.findByUsername(username);
        return user != null && passwordEncoder.matches(password, user.getPassword());
    }

    // 🔹 根据 ID 查询用户
    public User getUserById(Long id) {
        return userMapper.findUserById(id);
    }

     //  通过用户名获取用户
     public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    // 🔹 根据用户名查询用户
    public User findByUsername(String username) {
        return userMapper.findByUsername(username);
    }

    // 🔹 创建用户
    public User createUser(User user) {
        saveUser(user);
        return user;
    }

    // 🔹 存储用户（防止 Email 重复）
    @Transactional
    public void saveUser(User user) {
        if (userMapper.findByEmail(user.getEmail()) != null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email 已存在");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userMapper.insertUser(user);
    }

    // 🔹 更新用户信息
    @Transactional
    public void updateUser(Long id, User user) {
        User existingUser = userMapper.findUserById(id);
        if (existingUser == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }

        // 🔥 关键：防止 Email 更新时重复
        User userWithSameEmail = userMapper.findByEmail(user.getEmail());
        if (userWithSameEmail != null && !userWithSameEmail.getId().equals(id)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email 已存在");
        }

        existingUser.setUsername(user.getUsername());
        existingUser.setPassword(passwordEncoder.encode(user.getPassword())); // 密码加密
        existingUser.setEmail(user.getEmail());

        userMapper.updateUser(existingUser);
    }

    // 🔹 删除用户
    @Transactional
    public boolean deleteUser(Long id) {
        User existingUser = userMapper.findUserById(id);
        if (existingUser == null) {
            return false; // 用户不存在
        } 
        userMapper.deleteUser(id);
        return true; // 删除成功
    }
}
