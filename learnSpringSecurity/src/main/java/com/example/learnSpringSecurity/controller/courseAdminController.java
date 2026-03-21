package com.example.learnSpringSecurity.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.autoconfigure.graphql.GraphQlProperties.Http;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
// import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.learnSpringSecurity.DTO.CourseDTO.CourseReqDTO;
import com.example.learnSpringSecurity.DTO.CourseDTO.CourseResDTO;
import com.example.learnSpringSecurity.Entity.Course;
import com.example.learnSpringSecurity.Service.StudentService;
import com.example.learnSpringSecurity.Service.Implementation.CourseService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping
public class courseAdminController {
   
    @Autowired
    private CourseService courseService;
    
    

 

    @PostMapping("/admin/courses")
    public ResponseEntity<CourseResDTO> courseName(@RequestBody CourseReqDTO courseReqDto){

    //    return new ResponseEntity<>(courseService.createCourse(courseReqDto),HttpStatus.CREATED);
        return ResponseEntity.status(HttpStatus.CREATED).body(courseService.createCourse(courseReqDto));
    }

    // @PutMapping("/{courseId}")
    // public ResponseEntity<CourseResDTO> updateCourse(@PathVariable Long courseId, @RequestBody CourseReqDTO courseReqDto,   Pageable pageable){
    //   return new ResponseEntity<>(courseService.updateCourse(courseId,courseReqDto,pageable),HttpStatus.OK);
    // }
    
    // @PatchMapping("/{courseId}")
    // public ResponseEntity<CourseResDTO> updateCoursePartially(@PathVariable Long courseId, @RequestBody CourseReqDTO courseReqDto,Pageable pageable){
    //     return new ResponseEntity<>(courseService.updateCoursePartially(courseId,courseReqDto,pageable),HttpStatus.OK);
    // }

    // @DeleteMapping("/{courseId}")
    // public ResponseEntity<Void> deleteCourse(@PathVariable Long courseId){
        
    //     courseService.deleteCourse(courseId);
    //     return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    // }
    
    @GetMapping("{courseId}")
    public ResponseEntity<CourseResDTO> getCourseById(@PathVariable Long courseId,Pageable pageable){
        return new ResponseEntity<>(courseService.getCourseById(courseId,pageable),HttpStatus.OK);
    }

}
