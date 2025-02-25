package com.qxy.dyMall.utils;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;

public class JwtUtil {
    private static final SecretKey key = Keys.hmacShaKeyFor("your-256-bit-secret-your-256-bit-secret".getBytes(StandardCharsets.UTF_8));
    private static final long EXPIRATION_TIME = 1000 * 60 * 60 * 24 * 7; // 7 天有效期

    static {// 测试
        System.out.println("当前时间：" + new Date());
        System.out.println("Token 过期时间：" + new Date(System.currentTimeMillis() + EXPIRATION_TIME));
    }


    // 生成 JWT 令牌
    @SuppressWarnings("deprecation")
    public static String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    // 解析 JWT 令牌
    @SuppressWarnings("deprecation")
    public static String parseToken(String token) {
        try {
            return Jwts.parser()
                       .setSigningKey(key)
                       .build()
                       .parseClaimsJws(token)
                       .getBody()
                       .getSubject();
        } catch (Exception e) {
            System.out.println("JWT 解析失败: " + e.getMessage());
            return null; // Token 无效或过期
        }
    }
}
