package com.example.learnSpringSecurity.Repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.learnSpringSecurity.Entity.Student;

public interface StudentRepo extends JpaRepository<Student, Long> {
    
    @Query("select s from Student s join s.courses c where c.courseId = :courseId")
    Page<Student> findByCourseId(@Param("courseId") Long courseId, Pageable pageable);
    
    Student findByEmail(String email);
}
