package com.example.learnSpringSecurity.Repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.learnSpringSecurity.Entity.Student;

public interface StudentRepo extends JpaRepository<Student, Long> {
    
    @Query("select s from Student s join s.courses c where c.courseId = :courseId")
    List<Student> findByCourseId(@Param("courseId") Long courseId);
    
    Optional<Student> findByEmail(String email);
}
