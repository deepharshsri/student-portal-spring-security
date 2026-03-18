package com.example.learnSpringSecurity.DTO.StudentDTO;

import java.util.List;

import com.example.learnSpringSecurity.Entity.Course;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StudentReqDTO {

    public Long studentId;

    public String username;

    public String email;

    public String password;

    
}
