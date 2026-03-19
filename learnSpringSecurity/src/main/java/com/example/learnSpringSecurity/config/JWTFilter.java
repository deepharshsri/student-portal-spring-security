package com.example.learnSpringSecurity.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.learnSpringSecurity.Service.AuthService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Component
public class JWTFilter extends OncePerRequestFilter{
    
    @Autowired
    JwtServiceImpl jwtServiceImpl;
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response, 
                                    FilterChain filterChain) throws ServletException, IOException {
        
        try{ 

        String token=extractTokenFromHeader(request);
        if(token!=null){
         UserDetails userDetails=jwtServiceImpl.validateToken(token);
         UsernamePasswordAuthenticationToken authentication=new UsernamePasswordAuthenticationToken(
                                                                 userDetails,
                                                                 null,
                                                                 userDetails.getAuthorities()
         );
         SecurityContextHolder.getContext().setAuthentication(authentication);
        }
          }
        catch(Exception e){
         log.warn("Recieved Invalid Token");
        }
        
        filterChain.doFilter(request, response);
             
    }
    

    private String extractTokenFromHeader(HttpServletRequest request){
        String bearerToken=request.getHeader("Authorization");
        
        if(bearerToken!=null&&bearerToken.startsWith("Bearer ")){
            return bearerToken.substring(7);
        }
        return null;
    }
}
