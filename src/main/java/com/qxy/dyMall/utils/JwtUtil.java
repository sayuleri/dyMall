package com.qxy.dyMall.utils;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import com.qxy.dyMall.service.UserService;
import com.qxy.dyMall.model.User;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

@Component
public class JwtUtil {

    private static final String SECRET_KEY = "mysecretkeymysecretkeymysecretkey"; // 🔥 使用固定密钥
    private static final long EXPIRATION_TIME = 1000 * 60 * 60 * 24; // 1 天有效期

    @Autowired
    private UserService userService; // 注入用户服务

    /**
     * 生成 Token
     */
    @SuppressWarnings("deprecation")
    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * 从 Token 提取用户名
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * 通过用户名获取 userId
     */
    public Long getUserIdFromUsername(String username) {
        User user = userService.findByUsername(username);
        return user != null ? user.getId() : null;
    }

    /**
     * 校验 Token 是否有效
     */
    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    /**
     * 解析 Token 获取所有 Claims
     */
    @SuppressWarnings("deprecation")
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * 校验 Token 是否过期
     */
    private boolean isTokenExpired(String token) {
        return extractClaim(token, Claims::getExpiration).before(new Date());
    }

    /**
     * 提取 Token 中的某个属性
     */
    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
}
