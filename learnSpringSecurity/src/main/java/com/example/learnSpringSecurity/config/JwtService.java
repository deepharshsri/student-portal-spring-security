package com.example.learnSpringSecurity.config;

import java.security.Key;

import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {

 
    public Key getSignKey();
    UserDetails validateToken(String token);
    String extractToken(String token);
    
}
