package com.example.mytech.controller.admin;

import com.example.mytech.entity.Course;
import com.example.mytech.model.request.CourseRep;
import com.example.mytech.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CourseController {

    @Autowired
    private CourseService courseService;

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

    // Go to more courses page
    @GetMapping("/admin/course/create")
    public String getProductCreatePage(Model model, CourseRep req) {
        model.addAttribute("course", req);
        return "admin/course/create";
    }


}
