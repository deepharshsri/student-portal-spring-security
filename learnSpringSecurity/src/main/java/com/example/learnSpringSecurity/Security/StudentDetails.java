package com.example.learnSpringSecurity.Security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.example.learnSpringSecurity.Entity.Student;

public class StudentDetails implements UserDetails{

    private Student student;

    public StudentDetails(Student student){
        
        this.student=student;
    }
    
   @Override
public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of(new SimpleGrantedAuthority(student.getRole()));
    // student.getRole() → "ROLE_ADMIN" or "ROLE_USER"
}
    @Override
    public String getPassword() {
        // TODO Auto-generated method stub
        return student.getPassword();

    }
    @Override
    public String getUsername() {
        // TODO Auto-generated method stub
        return student.getEmail();
    }
    
    @Override
    public boolean isAccountNonExpired()  {   

       return true;
       }               


    @Override
    public boolean isAccountNonLocked() {   

       return true;
       }

    @Override
    public boolean isCredentialsNonExpired() {
       return true;
      }

    @Override
    public boolean isEnabled() {
  
        return true;
    }



}
