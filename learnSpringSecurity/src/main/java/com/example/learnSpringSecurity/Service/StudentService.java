package com.example.learnSpringSecurity.Service;

import com.example.learnSpringSecurity.controller.studentController;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.learnSpringSecurity.DTO.StudentDTO.StudentReqDTO;
import com.example.learnSpringSecurity.DTO.StudentDTO.StudentResDTO;
import com.example.learnSpringSecurity.Entity.Course;
import com.example.learnSpringSecurity.Entity.Student;
import com.example.learnSpringSecurity.Repository.CourseRepo;
import com.example.learnSpringSecurity.Repository.StudentRepo;

@Service
public class StudentService {
    


@Autowired
private StudentRepo studentRepo;

@Autowired
private CourseRepo courseRepo;



public List<Student> getAllStudents(Long courseId) {
    return studentRepo.findByCourseId(courseId);

}


public StudentResDTO createStudent(StudentReqDTO studentReqDto){
   
    Student newstudent = Student.builder()
    .username(studentReqDto.getUsername()).build();
    Student saved= studentRepo.save(newstudent);
    return StudentResDTO.builder().studentId(saved.getStudentId())
           .studentName(saved.getUsername()).build();
}

public StudentResDTO updateStudent(Long studentId, StudentReqDTO studentReqDto){
    Student updatedStudent = studentRepo.findById(studentId).map(student->{
        student.setUsername(studentReqDto.getUsername());
        return studentRepo.save(student);
    }).orElseThrow(()-> new RuntimeException("Student not found with id: " + studentId));
    return StudentResDTO.builder().studentId(updatedStudent.getStudentId())
           .studentName(updatedStudent.getUsername()).build();
}


public StudentResDTO updateStudentPartially(Long studentId,StudentReqDTO studentReqDto){

    Student updatedStudent = studentRepo.findById(studentId).map(student -> {
        Optional.ofNullable(studentReqDto.getUsername()).ifPresent(s-> student.setUsername(s));
        // Optional.ofNullable(studentReqDto.getCourses()).ifPresent(s-> student.setCourses(s));
        return studentRepo.save(student);
         }).orElseThrow(() -> new RuntimeException("Student not found with id: " + studentId));
    
        return StudentResDTO.builder().studentId(updatedStudent.getStudentId())
        .studentName(updatedStudent.getUsername()).build();

}

public void deleteStudent(Long studentId){
    studentRepo.deleteById(studentId);
}

public StudentResDTO getStudentById(Long studentId){
    Student student = studentRepo.findById(studentId).orElseThrow(() -> new RuntimeException("Student not found with id: " + studentId));
    return StudentResDTO.builder().studentId(student.getStudentId())
           .studentName(student.getUsername()).build();
}

public List<Course> getCourses(String email) {
    // TODO Auto-generated method stub
    
     Optional<Student> student= studentRepo.findByEmail(email);

     return student.map(s->{
        return s.getCourses();
     }).orElse(new ArrayList<>());

    }


public void addCourseById(String email, Long courseId) {
    // TODO Auto-generated method stub
        Optional<Student> student=studentRepo.findByEmail(email);
        
        if(student.isPresent()){
        List<Course> courses=student.get().getCourses();
        courses.add(courseRepo.findByCourseId(courseId));
        student.get().setCourses(courses);
        studentRepo.save(student.get());
        }
}
   
}



// public Page<Course> getCoursesByStudentid(Long studentId,Pageable pageable){
//     return courseRepo.findByStudentId(studentId, pageable);
// }

