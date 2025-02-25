package com.qxy.dyMall;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.qxy.dyMall.model.User;
import com.qxy.dyMall.service.UserService;

@MapperScan("com.qxy.dyMall.repository")
@SpringBootApplication
public class DyMallApplication {
    public static void main(String[] args) {
        // SpringApplication.run(DyMallApplication.class, args);
		ConfigurableApplicationContext context = SpringApplication.run(DyMallApplication.class, args);
        UserService userService = context.getBean(UserService.class);

        User testUser = new User();
        testUser.setUsername("testUser");
        testUser.setPassword("testPassword");
        testUser.setEmail("test@example.com");

        userService.saveUser(testUser);
    }
}

