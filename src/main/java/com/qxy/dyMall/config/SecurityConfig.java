package com.qxy.dyMall.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class SecurityConfig {
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    // @Bean
    // public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    //     http
    //         .csrf(csrf -> csrf.disable())  //  禁用 CSRF，解决 403 问题
    //         .authorizeHttpRequests(auth -> auth
    //             .requestMatchers("/api/users").permitAll()  //  允许所有用户访问 `POST /api/users`
    //             .requestMatchers("/api/users/**").permitAll() //  允许所有用户访问 `GET /api/users/{id}`
    //             .requestMatchers("/api/users/**").permitAll() //  允许 `PUT /api/users/{id}`
    //             .anyRequest().authenticated()  // 其他请求需要认证
    //         )
    //         .formLogin(form -> form.disable()) //  关闭 Spring Security 登录页面
    //         .httpBasic(httpBasic -> httpBasic.disable()); //  关闭 Basic Auth

    //     return http.build();
    // }

    //basic 认证替换成 JWT认证 👇

    @Autowired
    private JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // 关闭 CSRF
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/users/login", "/api/users/register").permitAll()
                .anyRequest().authenticated() // 其他接口需要认证
            )
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // 无状态
            .httpBasic(AbstractHttpConfigurer::disable) // ✅ 彻底禁用 Basic 认证
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class); // ✅ 确保 JWT 过滤器生效

        return http.build();
    }


}
