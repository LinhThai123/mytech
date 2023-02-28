package com.example.mytech.controller.admin;


import com.example.mytech.entity.Teacher;
import com.example.mytech.model.request.TeacherRep;
import com.example.mytech.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@Controller
public class TeacherController {
    @Autowired
    private TeacherService teacherService ;

    @PostMapping("/api/admin/teacher")
    public ResponseEntity<?> createCourse(@Valid @RequestBody TeacherRep req) {
        Teacher teacher = teacherService.createTeacher(req);
        return ResponseEntity.ok(teacher.getId());
    }
}
