package com.example.learnSpringSecurity.Security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.learnSpringSecurity.Entity.Student;
import com.example.learnSpringSecurity.Repository.StudentRepo;

@Service
public class StudentDetailsService implements UserDetailsService {
   
    @Autowired
    StudentRepo studentRepo;
    

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        

        Optional<Student> student= studentRepo.findByEmail(username);
        return student.map(StudentDetails::new).orElseThrow(()-> new UsernameNotFoundException("usename not found"));
    }
    
}
