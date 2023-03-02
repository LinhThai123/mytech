package com.example.mytech.controller.admin;


import com.example.mytech.entity.Teacher;
import com.example.mytech.model.request.TeacherRep;
import com.example.mytech.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@Controller
public class TeacherController {
    @Autowired
    private TeacherService teacherService;

    @GetMapping("/admin/teachers")
    public String teacherPages(Model model,
                               @RequestParam(defaultValue = "", required = false) String name,
                               @RequestParam(defaultValue = "", required = false) String email,
                               @RequestParam(defaultValue = "1", required = false) Integer page) {
        Page<Teacher> teachers = teacherService.adminGetListTeacher(name, email, page);
        model.addAttribute("teachers", teachers.getContent());
        model.addAttribute("totalPages", teachers.getTotalPages());
        model.addAttribute("currentPage", teachers.getPageable().getPageNumber() + 1);
        return "admin/teacher/list";
    }

    @PostMapping("/api/admin/teacher")
    public ResponseEntity<?> createCourse(@Valid @RequestBody TeacherRep req) {
        Teacher teacher = teacherService.createTeacher(req);
        return ResponseEntity.ok(teacher.getId());
    }

}
