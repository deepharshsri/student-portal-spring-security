package com.example.learnSpringSecurity.Entity;

import java.util.List;

import org.hibernate.engine.internal.Cascade;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Long studentId;
    private String username;
    private String email;
    private String password;
    private String role;

   
    @ManyToMany(mappedBy = "students")
    @JsonIgnore
    List<Course> courses;

    // Getters and Setters
 
   
}






    

