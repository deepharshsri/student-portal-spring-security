package com.example.learnSpringSecurity.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.learnSpringSecurity.DTO.StudentDTO.StudentReqDTO;
import com.example.learnSpringSecurity.DTO.StudentDTO.StudentResDTO;
import com.example.learnSpringSecurity.Entity.Course;
import com.example.learnSpringSecurity.Entity.Student;
import com.example.learnSpringSecurity.Service.StudentService;
import com.example.learnSpringSecurity.Service.Implementation.CourseService;

@RestController
@RequestMapping
public class studentController {
    
  

    @Autowired
    private StudentService studentService;
    
    @Autowired 
    private CourseService courseService;

    

    // @GetMapping
    // public ResponseEntity<Page<Student>> getAllStudents(Pageable pageable){
    //     return new ResponseEntity<>(studentService.getAllStudents(pageable),HttpStatus.OK);
    // } 

    @GetMapping("/students/me/courses")
    public ResponseEntity<List<Course>> getStudent(Authentication authentication){
    
    String email=authentication.getName();
    System.out.print(email);
    return ResponseEntity.ok(studentService.getCourses(email));
    }

     @GetMapping("/courses")
    public ResponseEntity<List<Course>> findAllCourses(Authentication authentication){
    
    
    return ResponseEntity.ok(new ArrayList<>(courseService.getAllCourses()));
    }
    
    @PostMapping("/students/me/enroll/{courseId}")
    public void addCourseById(@PathVariable Long courseId,Authentication authentication){
     String email=authentication.getName();
     System.out.print(email);
     studentService.addCourseById(email,courseId);
    }
    
    @PostMapping
    public ResponseEntity<StudentResDTO> createStudent(@RequestBody StudentReqDTO studentReqDTo){

        return new ResponseEntity<>(studentService.createStudent(studentReqDTo),HttpStatus.CREATED);

    }

    @PutMapping("/{studentId}")
    public ResponseEntity<StudentResDTO> updateStudent(@PathVariable Long studentId,@RequestBody StudentReqDTO studentReqDto){
         return new ResponseEntity<>(studentService.updateStudent(studentId,studentReqDto),HttpStatus.OK);

    }

    @PatchMapping("/{studentId}")
    public ResponseEntity<StudentResDTO> updateStudentPartially(@PathVariable Long studentId,@RequestBody StudentReqDTO studentReqDto){
       return new ResponseEntity<>(studentService.updateStudentPartially(studentId,studentReqDto),HttpStatus.OK);  
    }
 
    @DeleteMapping("/{studentId}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long studentId){    
        studentService.deleteStudent(studentId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/courses/{courseId}/students")
    public ResponseEntity<List<Student>> getStudentByCourseId(@PathVariable Long courseId){
     
        return ResponseEntity.ok(studentService.getAllStudents(courseId));

    }

 
    
}

