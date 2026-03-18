package com.example.learnSpringSecurity.DTO.CourseDTO;

import java.util.List;

import com.example.learnSpringSecurity.Entity.Student;


import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class CourseReqDTO {
    
    private Long courseId;

    private String courseName;
    
    List<Student> students;



}
