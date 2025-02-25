package com.qxy.dyMall.utils;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;


public class JwtUtil {
    @SuppressWarnings("deprecation")
    private static final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256); // 生成密钥
    private static final long EXPIRATION_TIME = 1000 * 60 * 60 * 24; // 1 天有效期

    // 生成 JWT 令牌
    @SuppressWarnings("deprecation")
    public static String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key)
                .compact();
    }

    // 解析 JWT 令牌
    public static String parseToken(String token) {
        try {
            return Jwts.parserBuilder()
                       .setSigningKey(key)
                       .build()
                       .parseClaimsJws(token)
                       .getBody()
                       .getSubject(); // 获取 subject
        } catch (Exception e) {
            return null; // Token 无效或过期
        }
    }
    
}