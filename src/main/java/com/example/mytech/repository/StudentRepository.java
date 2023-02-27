package com.example.mytech.repository;

import com.example.mytech.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, String> {

    boolean existsByEmail (String email);
}
