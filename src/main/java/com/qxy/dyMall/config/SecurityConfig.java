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

    @SuppressWarnings("removal")
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // 关闭 CSRF 保护（仅用于开发环境）
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/**").permitAll() // 允许访问 `/api/` 开头的所有接口
                .anyRequest().authenticated()) // 其他请求需要认证
            .formLogin(login -> login.disable()) // 关闭表单登录
            .httpBasic(basic -> basic.disable()); // 关闭 Basic Auth

        return http.build();
    }
}
