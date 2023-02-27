package com.example.mytech.service;

import com.example.mytech.entity.Student;
import com.example.mytech.model.request.StudentRep;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface StudentService {
    // get list student
    List<Student> getListStudent () ;

    // create student
    public Student createStudent(StudentRep rep);
}
