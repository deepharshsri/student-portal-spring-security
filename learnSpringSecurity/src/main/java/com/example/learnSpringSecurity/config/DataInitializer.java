package com.example.learnSpringSecurity.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.learnSpringSecurity.Entity.Student;
import com.example.learnSpringSecurity.Repository.StudentRepo;

@Component
public class DataInitializer implements CommandLineRunner{
  
    @Autowired
    StudentRepo studentRepo;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // TODO Auto-generated method stub
             Student student1=Student.builder().username("Deepansh")
                                        .email("Deepansh@gmail.com")
                                        .password(passwordEncoder.encode("password"))
                                        .role("ROLE_USER").build();

            
    studentRepo.save(student1);
    }
    
     
}
