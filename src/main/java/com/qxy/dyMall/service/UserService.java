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
    private UserMapper userMapper;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

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
    
    // @Transactional
    // public void saveUser(User user) {
    //     user.setPassword(passwordEncoder.encode(user.getPassword())); // 确保密码加密
    //     userMapper.insertUser(user);
    // }

    //test-password存储
    public void saveUser(User user) {
        if (userMapper.findByEmail(user.getEmail()) != null) {
            throw new RuntimeException("Email 已存在: " + user.getEmail());
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
    
        System.out.println("准备插入用户: " + user.getUsername()); 
        int rowsAffected = userMapper.insertUser(user);
        System.out.println("数据库影响行数: " + rowsAffected);
    
        if (rowsAffected > 0) {
            System.out.println("提交事务...");
        } else {
            throw new RuntimeException("插入失败，事务回滚");
        }
    }    
    
}