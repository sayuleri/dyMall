package com.qxy.dyMall.filter;

import com.qxy.dyMall.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;

    @Autowired
    public JwtFilter(JwtUtil jwtUtil, UserDetailsService userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        System.out.println("🔍 收到请求: " + requestURI);

        // 🔥 放行 /register 和 /login 请求，不需要 JWT
        if (requestURI.startsWith("/api/users/register") || requestURI.startsWith("/api/users/login")) {
            chain.doFilter(request, response);
            return;
        }

        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            System.out.println("🚨 Authorization 头部为空或格式错误");
            chain.doFilter(request, response);
            return;
        }

        System.out.println("🔍 收到请求: " + request.getRequestURI());

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            System.out.println("🚨 Authorization 头部为空或格式错误: " + authHeader);
            chain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring(7);
        System.out.println("🔑 提取的 Token: " + token);

        try {
            String username = jwtUtil.extractUsername(token);
            System.out.println("✅ 解析出的用户名: " + username);

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                if (jwtUtil.validateToken(token, userDetails)) {
                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                    System.out.println("✅ JWT 认证成功，用户: " + username);
                } else {
                    System.out.println("❌ JWT 认证失败: Token 无效");
                }
            }
        } catch (Exception e) {
            System.out.println("❌ JWT 解析失败: " + e.getMessage());
        }

        chain.doFilter(request, response);
        ;
    }
}
