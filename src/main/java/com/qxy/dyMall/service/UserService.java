package com.qxy.dyMall.service;

import com.qxy.dyMall.mapper.UserMapper;
import com.qxy.dyMall.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public User getUserById(int id) {
        return userMapper.getUserById(id);
    }
}
