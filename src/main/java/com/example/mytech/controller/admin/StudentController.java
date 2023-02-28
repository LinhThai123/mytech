package com.example.mytech.controller.admin;

import com.example.mytech.entity.Student;
import com.example.mytech.model.request.StudentRep;
import com.example.mytech.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import javax.validation.Valid;

@Controller
public class StudentController {

    @Autowired
    private StudentService studentService;


    @PostMapping("/api/admin/student")
    public ResponseEntity<?> createStudent(@Valid @RequestBody StudentRep req) {
        Student student = studentService.createStudent(req);
        return ResponseEntity.ok(student);
    }
}
