package com.example.mytech.service;

import com.example.mytech.entity.Category;
import com.example.mytech.entity.Teacher;
import com.example.mytech.model.request.TeacherRep;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TeacherService {

    public Teacher getTeacherById(String id);

    public List<Teacher> getListTeacher();

    public Teacher createTeacher(TeacherRep req);
}
