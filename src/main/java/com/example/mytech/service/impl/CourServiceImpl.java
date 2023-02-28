package com.example.mytech.service.impl;

import com.example.mytech.config.Contant;
import com.example.mytech.entity.Course;
import com.example.mytech.exception.BadRequestException;
import com.example.mytech.model.request.CourseRep;
import com.example.mytech.repository.CourseRepository;
import com.example.mytech.service.CourseService;
import com.github.slugify.Slugify;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Component
public class CourServiceImpl implements CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Override
    public Page<Course> adminGetListProduct(String id, String name, Integer page) {
        page--;
        if (page < 0) {
            page = 0;
        }
        Pageable pageable = PageRequest.of(page, Contant.LIMIT_COURSE, Sort.by("createdAt").descending());
        return courseRepository.findCourseByIdOrNameContaining(id, name, pageable);
    }

    @Override
    public Course createCourse(CourseRep rep) {

        Course course = new Course();

        //check exits name
        if(courseRepository.existsByName(rep.getName())) {
            throw new BadRequestException("Tên khóa học đã tồn tại");
        }
        course.setName(rep.getName());
        // set slug
        Slugify slg = new Slugify() ;
        course.setSlug(slg.slugify(rep.getName()));

        // set price
        course.setPrice(rep.getPrice());

        course.setDescription(rep.getDescription());
        course.setImage(rep.getImage());

        if (rep.isPublic()) {
            // Public post
            if (rep.getDescription().isEmpty()) {
                throw new BadRequestException("Để công khai khóa học vui lòng nhập mô tả ");
            }
            if(rep.getImage().isEmpty()) {
                throw new BadRequestException("Để công khai khóa học vui lòng nhập mô tả ");
            }
            course.setPublishedAt(new Timestamp(System.currentTimeMillis()));
        }

        course.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        return null;
    }
}
