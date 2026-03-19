package com.example.learnSpringSecurity.config;

import java.security.Key;

import org.hibernate.annotations.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtServiceImpl implements JwtService  {
    
    @Autowired
    UserDetailsService userDetailsService;

    @Value("${jwt.secretKey}")
    String secretKey;

    @Override
    public Key getSignKey() {
      byte[] key=secretKey.getBytes();
      return Keys.hmacShaKeyFor(key);
    }

    @Override
    public UserDetails validateToken(String token) {
        String bearerToken=extractToken(token);
        return userDetailsService.loadUserByUsername(bearerToken);
    }

    @Override
    public String extractToken(String token) {
        Claims claims =Jwts.parserBuilder()
                       .setSigningKey(getSignKey())
                       .build()
                       .parseClaimsJws(token)
                       .getBody();
       return claims.getSubject();
    }
    
}
