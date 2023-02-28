package com.example.mytech.service;

import com.example.mytech.entity.Course;
import com.example.mytech.model.request.CourseRep;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public interface CourseService {

    // get list course và page
    Page<Course> adminGetListProduct(String id, String name, Integer page);

    // create course
    public Course createCourse (CourseRep rep) ;

}
