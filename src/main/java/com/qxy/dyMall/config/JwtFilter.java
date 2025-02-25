package com.qxy.dyMall.config;

import com.qxy.dyMall.utils.JwtUtil;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;

import javax.crypto.SecretKey;

@Component
public class JwtFilter implements Filter {
    private static final SecretKey key = Keys.hmacShaKeyFor("your-256-bit-secret-your-256-bit-secret".getBytes(StandardCharsets.UTF_8));
    private static final long EXPIRATION_TIME = 1000 * 60 * 60 * 24 * 7; // 1 天有效期
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        // 允许访问登录和注册接口
        String path = req.getRequestURI();
        if (path.startsWith("/api/users/login") || path.startsWith("/api/users/register")) {
            chain.doFilter(request, response);
            return;
        }

        
        // 获取 JWT 令牌
        String token = req.getHeader("Authorization");
        System.out.println("Authorization Header: " + token);//是否成功获取到 Authorization
        Enumeration<String> headerNames = req.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String header = headerNames.nextElement();
            System.out.println(header + ": " + req.getHeader(header));
        }

        // 验证 JWT
        if (token == null || !token.startsWith("Bearer ")) {
            System.out.println("❌ 401: 未提供 JWT");
            res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            res.getWriter().write("未授权访问");
            return;
        }

        // 验证 JWT-测试
        try {
        System.out.println("收到的 Token: " + token);
        String jwt = token.replace("Bearer ", "").trim();
        
        @SuppressWarnings("deprecation")
        Claims claims = Jwts.parser()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(jwt)
                .getBody();

        System.out.println("解析的用户名: " + claims.getSubject());
        req.setAttribute("user", claims.getSubject());
    } catch (Exception e) {
        System.out.println("JWT 解析失败: " + e.getMessage());
        res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        res.getWriter().write("无效的 Token");
        return;
    }

        // 解析 JWT
        token = token.replace("Bearer ", "");
        String username = JwtUtil.parseToken(token);
        if (username == null) {
            System.out.println("❌ 401: JWT 解析失败");
            res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            res.getWriter().write("无效的 Token");
            return;
        }

        // 继续执行请求
        System.out.println("✅ 用户 " + username + " 通过 JWT 验证");
        chain.doFilter(request, response);
    }
}

