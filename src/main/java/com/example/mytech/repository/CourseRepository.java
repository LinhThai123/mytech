package com.example.mytech.repository;

import com.example.mytech.entity.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Course, String> {

    // get list course and find name
    Page<Course> findCourseByIdOrNameContaining (String id , String name , Pageable pageable) ;

    boolean existsByName (String name) ;

    // count course of category
    Long countByCategoryId (String category_id);

}
