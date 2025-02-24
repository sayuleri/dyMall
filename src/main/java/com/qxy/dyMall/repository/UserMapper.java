package com.qxy.dyMall.repository;

import com.qxy.dyMall.model.User;
import org.apache.ibatis.annotations.*;
import org.mybatis.spring.annotation.MapperScan;

@Mapper
@MapperScan("com.qxy.dyMall.repository")
public interface UserMapper {
    @Insert("INSERT INTO users (username, password, email) VALUES (#{username}, #{password}, #{email})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insertUser(User user);// 插入用户

    @Select("SELECT * FROM users WHERE username = #{username}")
    User findByUsername(String username);// 根据用户名查找用户

    @Select("SELECT * FROM users WHERE id = #{id}")
    User findUserById(long id);

    User save(User user);// 保存用户

    @Select("SELECT * FROM users WHERE email = #{email}")
    User findByEmail(String email);


}