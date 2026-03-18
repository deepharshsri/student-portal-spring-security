package com.example.learnSpringSecurity.Service.Implementation;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.stereotype.Service;


import com.example.learnSpringSecurity.Repository.StudentRepo;
import com.example.learnSpringSecurity.Security.StudentDetailsService;
import com.example.learnSpringSecurity.Service.AuthService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;



@Service
public class AuthServiceImpl  implements AuthService {
    
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    StudentDetailsService userDetailsService;
    @Autowired
    StudentRepo studentRepo;

    @Value("${jwt.secretKey}")
    String secretKey;

    Long expirationTime = 86400000L;

    @Override
    public UserDetails authenticate(String username, String password) {
        // Implement authentication logic here
         System.out.println("Username received: " + username); // ← add this
         System.out.println("Password received: " + password);
         try {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(username, password)
        );
        System.out.println("Authentication successful!"); // ← add this
    } catch (Exception e) {
        System.out.println("Auth failed: " + e.getMessage()); // ← add this
    }
       return userDetailsService.loadUserByUsername(username);
    }

    @Override
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", userDetails.getAuthorities().iterator().next().getAuthority());
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+ expirationTime))
                .signWith(getSignKey(),SignatureAlgorithm.HS256)
                .compact();
            }

    private Key getSignKey(){

      byte[] key=secretKey.getBytes();
      return Keys.hmacShaKeyFor(key);
    }

    @Override
    public UserDetails validateToken(String token) {
        String bearerToken=extractToken(token);
        return userDetailsService.loadUserByUsername(bearerToken);
        
    }

    private String extractToken(String token) {
       
        Claims claims =Jwts.parserBuilder()
                       .setSigningKey(getSignKey())
                       .build()
                       .parseClaimsJws(token)
                       .getBody();
       return claims.getSubject();
    }
    
}
