package com.example.mytech.service;

import com.example.mytech.entity.Course;
import com.example.mytech.model.request.CourseRep;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CourseService {

    // get list course api
    List<Course> getListCourse () ;

    public Course getCourseById (String id) ;

    // get list course và page
    Page<Course> adminGetListCourses(String id, String name, Integer page);

    // create course
    public Course createCourse (CourseRep rep) ;

    // update course
    public Course updateCourse (String id , CourseRep rep) ;

    // delete coure theo id
    public void deleteCourse (Course course) ;

}
