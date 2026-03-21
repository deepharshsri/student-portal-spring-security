package com.example.learnSpringSecurity.Service.Implementation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.learnSpringSecurity.DTO.CourseDTO.CourseReqDTO;
import com.example.learnSpringSecurity.DTO.CourseDTO.CourseResDTO;
import com.example.learnSpringSecurity.Entity.Course;

import com.example.learnSpringSecurity.Repository.CourseRepo;
import com.example.learnSpringSecurity.Repository.StudentRepo;


@Service
public class CourseService {

    @Autowired
    private CourseRepo courseRepo;

    @Autowired
    private StudentRepo studentRepo;
    
    public List<Course> getAllCourses() {
        return new ArrayList<>(courseRepo.findAll());
    }
    
    public CourseResDTO createCourse( CourseReqDTO courseReqDTo){
       
       
       Course course= Course.builder()
       .courseName(courseReqDTo.getCourseName())
       .students(courseReqDTo.getStudents()).build();
       Course saved= courseRepo.save(course);
       CourseResDTO courseResDTO= CourseResDTO.builder().courseId(saved.getCourseId())
       .courseName(saved.getCourseName()).build();
       return courseResDTO;
    }
    
    // public CourseResDTO updateCourse(Long courseId, CourseReqDTO courseReqDto,Pageable pageable){
    //     return courseRepo.findById(courseId).map(
    //         course->{
    //             course.setCourseName(courseReqDto.getCourseName());
    //             course.setStudents(courseReqDto.getStudents());
    //             Course updated= courseRepo.save(course);
    //             CourseResDTO courseResDTO= CourseResDTO.builder().courseId(updated.getCourseId())
    //             .courseName(updated.getCourseName()).students(studentRepo.findByCourseId(courseId)).build();
    //             return courseResDTO;
    //         }
    //     ).orElseThrow(()-> new RuntimeException("Course not found with ID: " + courseId));
    // }

    // public CourseResDTO updateCoursePartially(Long courseId, CourseReqDTO courseReqDTO,Pageable pageable){
    //     return courseRepo.findById(courseId).map(
    //         course -> {
    //             Optional.ofNullable(courseReqDTO.getCourseName()).ifPresent(courseName->{course.setCourseName(courseName);});
    //             Optional.ofNullable(courseReqDTO.getStudents()).ifPresent(course::setStudents);
    //             Course updated= courseRepo.save(course);
    //             CourseResDTO courseResDTO= CourseResDTO.builder().courseId(updated.getCourseId())
    //             .courseName(updated.getCourseName()).students(studentRepo.findByCourseId(courseId)).build();
    //             return courseResDTO;
    //         }
    //     ).orElseThrow(RuntimeException::new);
    // }

    // public void deleteCourse(Long courseId){

    //     Course course= courseRepo.findById(courseId).orElseThrow(RuntimeException::new);
    //     courseRepo.delete(course);
    // }

    public CourseResDTO getCourseById(Long courseId,Pageable pageable){
    
        return courseRepo.findById(courseId).map(course->{
           
           return  CourseResDTO.builder().courseId(course.getCourseId()).
            courseName(course.getCourseName()).
            students(studentRepo.findByCourseId(courseId)).build();
        }).orElseThrow(RuntimeException::new);
    

    }

}
