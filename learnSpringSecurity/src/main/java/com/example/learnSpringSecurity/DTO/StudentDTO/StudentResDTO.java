package com.example.learnSpringSecurity.DTO.StudentDTO;


import org.springframework.data.domain.Page;

import com.example.learnSpringSecurity.Entity.Course;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class StudentResDTO {
    
    public Long studentId;

    public String studentName;

    public Page<Course> courses;
}


