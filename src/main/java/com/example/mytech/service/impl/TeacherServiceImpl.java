package com.example.mytech.service.impl;

import com.example.mytech.entity.Category;
import com.example.mytech.entity.Teacher;
import com.example.mytech.exception.NotFoundException;
import com.example.mytech.model.request.TeacherRep;
import com.example.mytech.repository.TeacherRepository;
import com.example.mytech.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
public class TeacherServiceImpl implements TeacherService {
    @Autowired
    private TeacherRepository teacherRepository;
    @Override
    public Teacher getTeacherById(String id) {
        Optional<Teacher> teacher = teacherRepository.findById(id);
        if(teacher.isEmpty()){
            throw new NotFoundException("Không tìm thấy danh mục");
        }
        return teacher.get();
    }

    @Override
    public List<Teacher> getListTeacher() {
        return teacherRepository.findAll();
    }

    @Override
    public Teacher createTeacher(TeacherRep req) {
        Teacher teacher = new Teacher() ;
        teacher.setName(req.getName());
        teacher.setAddress(req.getAddress());
        teacher.setEmail(req.getEmail());
        teacher.setPassword(req.getPassword());
        teacher.setRoles(new ArrayList<>(Arrays.asList("TEACHER")));
        teacherRepository.save(teacher);
        return teacher ;
    }
}
