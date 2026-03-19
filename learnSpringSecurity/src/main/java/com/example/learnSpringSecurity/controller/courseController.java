package com.example.learnSpringSecurity.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.learnSpringSecurity.Entity.Course;
import com.example.learnSpringSecurity.Service.Implementation.CourseService;
@RestController
@RequestMapping("/courses")
public class courseController {
    

    @Autowired
    CourseService courseService;

     @GetMapping
    public ResponseEntity<List<Course>> getAllCourse(){
        return new ResponseEntity<>(courseService.getAllCourses(),HttpStatus.OK);
       
    }
}
