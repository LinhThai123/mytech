package com.example.mytech.service.impl;

import com.example.mytech.config.Contant;
import com.example.mytech.entity.Category;
import com.example.mytech.entity.Course;
import com.example.mytech.entity.Teacher;
import com.example.mytech.exception.BadRequestException;
import com.example.mytech.exception.InternalServerException;
import com.example.mytech.model.request.CourseRep;
import com.example.mytech.repository.CourseRepository;
import com.example.mytech.service.CategoryService;
import com.example.mytech.service.CourseService;
import com.example.mytech.service.TeacherService;
import com.github.slugify.Slugify;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Component
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private CategoryService categoryService ;

    @Autowired
    private TeacherService teacherService ;

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

        // check end_at
        Timestamp now = new Timestamp(System.currentTimeMillis());
        if (rep.getEndAt().before(now)) {
            throw new BadRequestException("Hạn kết thúc khóa học không hợp lệ");
        }

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

        if (rep.isPublic()) {
            // Public post
            if (rep.getDescription().isEmpty()) {
                throw new BadRequestException("Để công khai khóa học vui lòng nhập mô tả ");
            }
            if(rep.getImage().isEmpty()) {
                throw new BadRequestException("Để công khai khóa học vui lòng thêm ảnh ");
            }
            course.setPublishedAt(new Timestamp(System.currentTimeMillis()));
        }

        // image of course
        course.setImage(rep.getImage());
        course.setLevel(rep.getLevel());

        // get list category by id
        if(rep.getCategory_id().isEmpty()) {
            throw new BadRequestException("Vui lòng chọn danh mục cho khóa học");
        }
        Category category = categoryService.getCategoryById(rep.getCategory_id());
        course.setCategory(category);

        // gte list teacher by id
        if(rep.getTeacher_id().isEmpty()) {
            throw new BadRequestException("Vui lòng chọn giảng viên cho khóa học");
        }
        Teacher teacher = teacherService.getTeacherById(rep.getTeacher_id());
        course.setTeacher(teacher);

        course.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        course.setModifiedAt(new Timestamp(System.currentTimeMillis()));

        try{
            courseRepository.save(course);
            return course ;
        }
        catch (Exception e) {
            throw new InternalServerException("Lỗi khi thêm khóa học");
        }
    }
}
