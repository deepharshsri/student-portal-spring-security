package com.example.learnSpringSecurity.Entity;

import java.util.List;
// import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import lombok.Builder;
import lombok.Data;

@Entity
@Data
@Builder
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long courseId;

    private String courseName;
    
    @ManyToMany
    @JoinTable(
        name = "student_course",
        joinColumns = @JoinColumn(name = "student_id"),
        inverseJoinColumns = @JoinColumn(name = "course_id")
    )   
    List<Student> students;

  
}
