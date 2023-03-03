package com.example.mytech.service.impl;

import com.example.mytech.config.Contant;
import com.example.mytech.entity.Category;
import com.example.mytech.entity.Course;
import com.example.mytech.entity.Teacher;
import com.example.mytech.exception.BadRequestException;
import com.example.mytech.exception.InternalServerException;
import com.example.mytech.exception.NotFoundException;
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
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Component
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private CategoryService categoryService ;

    @Autowired
    private TeacherService teacherService ;

    @Override
    public List<Course> getListCourse() {
        return courseRepository.findAll();
    }

    @Override
    public Course getCourseById(String id) {
        Optional<Course> course = courseRepository.findById(id) ;
        if(!course.isPresent()) {
            throw new NotFoundException("Course do not exits");
        }
        return course.get();
    }

    @Override
    public Page<Course> adminGetListCourses(String id, String name, Integer page) {
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

         //check end_at
        LocalDate date = LocalDate.now();
        if (rep.getExpiredAt().isBefore(date)) {
            throw new BadRequestException("Hạn kết thúc khóa học không hợp lệ");
        }
        course.setExpiredAt(rep.getExpiredAt().plusDays(1));
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

        if (rep.getActive() == Contant.PUBLIC_COURSE) {
            // Public post
            if (rep.getDescription().isEmpty()) {
                throw new BadRequestException("Để công khai khóa học vui lòng nhập mô tả ");
            }
//            if(rep.getImage().isEmpty()) {
//                throw new BadRequestException("Để công khai khóa học vui lòng thêm ảnh ");
//            }
            course.setPublishedAt(new Timestamp(System.currentTimeMillis()));
        }
        course.setActive(rep.getActive());

        course.setStatus(rep.getStatus());
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

        }
        catch (Exception e) {
            throw new InternalServerException("Lỗi khi thêm khóa học");
        }
        return course;
    }

    @Override
    public Course updateCourse(String id, CourseRep rep) {
        Course course;
        Optional<Course> rs = courseRepository.findById(id);
        course = rs.get();
        if(!rs.isPresent()) {
            throw new NotFoundException("Course do not exits");
        }
//        if(courseRepository.existsByName(rep.getName())) {
//            throw new BadRequestException("Tên khóa học đã tồn tại");
//        }
        course.setName(rep.getName());

        // set slug
        Slugify slg = new Slugify() ;
        course.setSlug(slg.slugify(rep.getName()));

        // set price
        course.setPrice(rep.getPrice());

        course.setDescription(rep.getDescription());

        if (rep.getActive() == Contant.PUBLIC_COURSE) {
//            if(rep.getImage().isEmpty()) {
//                throw new BadRequestException("Để công khai khóa học vui lòng thêm ảnh ");
//            }
            course.setPublishedAt(new Timestamp(System.currentTimeMillis()));
        }
        course.setActive(rep.getActive());

        course.setStatus(rep.getStatus());
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
        course.setId(id);
        try{
            courseRepository.save(course);
            return course;
        }
        catch (Exception e) {
            throw new InternalServerException("Lỗi khi sửa khóa học");
        }

    }
    @Override
    public void deleteCourse(Course course) {
        courseRepository.delete(course);
    }
}
