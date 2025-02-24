package com.qxy.dyMall.mapper;

import com.qxy.dyMall.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {

    // 用 @Select 注解定义 SQL（也可以使用 XML 映射）
    @Select("SELECT * FROM users WHERE id = #{id}")
    User getUserById(int id);
}
