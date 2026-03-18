package com.example.learnSpringSecurity.DTO.CourseDTO;



import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.learnSpringSecurity.Entity.Student;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CourseResDTO {

        public Long courseId;
    
        public String courseName;  
        
        public Page<Student> students;
        
     
}