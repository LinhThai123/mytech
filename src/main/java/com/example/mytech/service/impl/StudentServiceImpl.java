package com.example.mytech.service.impl;

import com.example.mytech.entity.Student;
import com.example.mytech.exception.BadRequestException;
import com.example.mytech.model.request.StudentRep;
import com.example.mytech.repository.StudentRepository;
import com.example.mytech.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class StudentServiceImpl implements StudentService {
    @Autowired
    private StudentRepository studentRepository ;
    @Override
    public List<Student> getListStudent() {
        return studentRepository.findAll();
    }

    @Override
    public Student createStudent(StudentRep rep) {
        Student student = new Student();

        student.setName(rep.getName());


        student.setPassword(rep.getPassword());
        // check trungf email
        if(studentRepository.existsByEmail(rep.getEmail())){
            throw new BadRequestException("Email already exists");
        }
        student.setEmail(rep.getEmail());

        student.setPhone(rep.getPhone());

        student.setAddress(rep.getAddress());

        student.setAvatar(rep.getAvatar());

        student.setCreatedAt(new Timestamp(System.currentTimeMillis()));

        student.setStatus(true);

        student.setRoles(new ArrayList<>(Arrays.asList("STUDENT")));

        studentRepository.save(student);
        return student;
    }
}
