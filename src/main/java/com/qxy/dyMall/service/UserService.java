package com.qxy.dyMall.service;

import com.qxy.dyMall.model.User;
import com.qxy.dyMall.repository.UserMapper;
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

    public boolean loginUser(String username, String password) {
        User user = userMapper.findByUsername(username);
        return user != null && passwordEncoder.matches(password, user.getPassword());
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
}