package com.example.mytech.controller.admin;

import com.example.mytech.entity.Course;
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

    @GetMapping("/admin/course")
    public String getAdminProducts(Model model,
                                   @RequestParam(defaultValue = "", required = false) String id,
                                   @RequestParam(defaultValue = "", required = false) String name,
                                   @RequestParam(defaultValue = "1", required = false) Integer page) {
        // get list course
        Page<Course> courses = courseService.adminGetListProduct(id, name, page);
        model.addAttribute("course", courses.getContent());
        model.addAttribute("totalPages", courses.getTotalPages());
        model.addAttribute("currentPage", courses.getPageable().getPageNumber() + 1);
        return "admin/course/list";
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
    @PostMapping("/admin/course/create")
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
        Course courseId = courseService.createCourse(req);
        return ResponseEntity.ok(courseId.getId());
    }
}
