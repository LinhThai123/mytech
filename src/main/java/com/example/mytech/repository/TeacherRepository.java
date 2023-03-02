package com.example.mytech.repository;

import com.example.mytech.entity.Teacher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher , String> {

    //check email
    boolean existsByEmail (String email);

    Page<Teacher> findByNameContainingOrEmail(String name, String email, Pageable pageable);
}
