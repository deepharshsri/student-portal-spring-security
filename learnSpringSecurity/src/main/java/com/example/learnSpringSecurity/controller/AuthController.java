package com.example.learnSpringSecurity.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.learnSpringSecurity.DTO.AuthResponse;
import com.example.learnSpringSecurity.DTO.Login;
import com.example.learnSpringSecurity.Service.AuthService;

@RestController
@RequestMapping("/auth/login")
public class AuthController {
  
  @Autowired
  AuthService authService;

    @PostMapping
    public ResponseEntity<AuthResponse> login(@RequestBody Login login){

        UserDetails userDetails=  authService.authenticate(login.getUsername(),login.getPassword());
        String token= authService.generateToken(userDetails);

        AuthResponse authResponse=AuthResponse.builder()
                                              .token(token)
                                              .role(userDetails.getAuthorities().iterator().next().getAuthority())
                                              .expiryTime("86400").build();
       return ResponseEntity.ok(authResponse);

        
    }
}
