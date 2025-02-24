package com.qxy.dyMall.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class SecurityConfig {
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())  // 🚀 禁用 CSRF，解决 403 问题
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/users").permitAll()  // 🚀 允许所有用户访问 `POST /api/users`
                .requestMatchers("/api/users/**").permitAll() // 🚀 允许所有用户访问 `GET /api/users/{id}`
                .anyRequest().authenticated()  // 其他请求需要认证
            )
            .formLogin(form -> form.disable()) // 🚀 关闭 Spring Security 登录页面
            .httpBasic(httpBasic -> httpBasic.disable()); // 🚀 关闭 Basic Auth

        return http.build();
    }
}
