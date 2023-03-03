package com.example.mytech.controller.admin;

import com.example.mytech.entity.Course;
import com.example.mytech.exception.NotFoundException;
import com.example.mytech.model.request.CategoryRep;
import com.example.mytech.model.request.CourseRep;
import com.example.mytech.model.request.TeacherRep;
import com.example.mytech.service.CategoryService;
import com.example.mytech.service.CourseService;
import com.example.mytech.service.TeacherService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class CourseController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private TeacherService teacherService;

    ModelMapper modelMapper = new ModelMapper();

    @GetMapping("/admin/courses")
    public String getAdminProducts(Model model,
                                   @RequestParam(defaultValue = "", required = false) String id,
                                   @RequestParam(defaultValue = "", required = false) String name,
                                   @RequestParam(defaultValue = "1", required = false) Integer page) {
        // get list course
        Page<Course> courses = courseService.adminGetListCourses(id, name, page);
        model.addAttribute("courses", courses.getContent());
        model.addAttribute("totalPages", courses.getTotalPages());
        model.addAttribute("currentPage", courses.getPageable().getPageNumber() + 1);
        return "admin/course/list";
    }
    //get list course no page
    @GetMapping ("/api/admin/course")
    public ResponseEntity<Object> getListCourse () {
        List<Course> courses = courseService.getListCourse();
        return ResponseEntity.ok(courses);
    }

    // get list category
    @ModelAttribute("categories")
    public List<CategoryRep> getCategories() {
        return categoryService.getListCategory().stream()
                .map(item -> {
                    CategoryRep rep = new CategoryRep();
                    modelMapper.map(item, rep);
                    return rep;
                }).collect(Collectors.toList());
    }

    @ModelAttribute("teachers")
    public List<TeacherRep> getTeachers() {
        return teacherService.getListTeacher().stream()
                .map(item -> {
                    TeacherRep rep = new TeacherRep();
                    modelMapper.map(item, rep);
                    return rep;
                }).collect(Collectors.toList());
    }

    // Go to more courses page
    @GetMapping("/admin/course/create")
    public String getProductCreatePage(Model model, CourseRep rep) {
        model.addAttribute("course" , rep);
        return "admin/course/create";
    }

    // add course by Server Side
    @PostMapping("/admin/courses/create")
    public ModelAndView createCourse(ModelMap model, @Valid @ModelAttribute(value = "course") CourseRep req, BindingResult result) {
        if (result.hasErrors()) {
            return new ModelAndView("admin/course/create");
        }

        Course course = courseService.createCourse(req);
        model.addAttribute("course", course);
        return new ModelAndView("redirect:/admin/courses", model);
    }
    // add course by API
    @PostMapping("/api/admin/courses")
    public ResponseEntity<?> createCourse(@Valid @RequestBody CourseRep req) {
        Course course = courseService.createCourse(req);
        return ResponseEntity.ok(course);
    }

    // Go to page details
    @GetMapping("/admin/courses/{id}")
    public String getDetailProducPage(Model model, @PathVariable("id") String id) {
        try {
            // Get info
            Course course = courseService.getCourseById(id);
            model.addAttribute("course", course);

            return "admin/course/details";
        } catch (NotFoundException ex) {
            return "admin/error/err";
        }
    }

    //update code thymleaf
    @PostMapping ("/admin/courses/update/{id}")
    public ModelAndView updateCourse(@Valid @PathVariable String id, @ModelAttribute("course") CourseRep req, ModelMap model, BindingResult result) {
        if (result.hasErrors()) {
            req.setId(id);
            return new ModelAndView("redirect:/admin/course/details", model);
        }
        Course course = courseService.updateCourse(id, req);
        model.addAttribute("course", course);
        return new ModelAndView("redirect:/admin/courses", model);
    }
    // update course api
    @PutMapping("/api/admin/courses/update/{id}")
    public ResponseEntity<?> updateCourse (@PathVariable("id") String id, @Valid @RequestBody CourseRep rep) {
        courseService.updateCourse(id, rep);
        return ResponseEntity.ok("Cập nhật thành công");
    }

    // delete course api
    @DeleteMapping("/api/admin/courses/delete/{id}")
    public ResponseEntity<?> deleteCourse (@PathVariable("id") Course id) {
        courseService.deleteCourse(id);
        return ResponseEntity.ok("Xóa thàng công");
    }

    // delete product by id follow server side
    @GetMapping("/admin/courses/delete/{id}")
    public ModelAndView deleteCourse (ModelMap model, @PathVariable("id") String id) {
        Optional<Course> rs = Optional.ofNullable(courseService.getCourseById(id));
        if(rs.isPresent()){
            courseService.deleteCourse(rs.get());
            model.addAttribute("message", "Xóa thành công");
            return new ModelAndView("redirect:/admin/courses", model);
        }
        else {
            model.addAttribute("message", "Xóa thất bại");
            return new ModelAndView("redirect:/admin/courses", model);
        }
    }
}
