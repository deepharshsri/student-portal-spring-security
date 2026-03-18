package com.example.learnSpringSecurity.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.learnSpringSecurity.Entity.Course;

@Repository
public interface CourseRepo extends JpaRepository<Course, Long> {
// 
// @Query(Select c from course c )

}
