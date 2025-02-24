package com.qxy.dyMall.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()  // 关闭 CSRF 保护，允许 POST
            .authorizeHttpRequests()
            .requestMatchers("/users").permitAll()  // 允许 /users 访问
            .anyRequest().authenticated()
            .and()
            .httpBasic();  // 启用 Basic Auth
        return http.build();
    }
}
