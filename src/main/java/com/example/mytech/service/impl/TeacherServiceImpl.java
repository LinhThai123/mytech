package com.example.mytech.service.impl;

import com.example.mytech.config.Contant;
import com.example.mytech.entity.Teacher;
import com.example.mytech.exception.BadRequestException;
import com.example.mytech.exception.InternalServerException;
import com.example.mytech.exception.NotFoundException;
import com.example.mytech.model.request.TeacherRep;
import com.example.mytech.repository.TeacherRepository;
import com.example.mytech.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
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
        if (teacher.isEmpty()) {
            throw new NotFoundException("Không tìm thấy giảng viên");
        }
        return teacher.get();
    }

    @Override
    public List<Teacher> getListTeacher() {
        return teacherRepository.findAll();
    }

    @Override
    public Teacher createTeacher(TeacherRep req) {

        Teacher teacher = new Teacher();

        teacher.setName(req.getName());

        teacher.setGender(req.getGender());

        teacher.setAddress(req.getAddress());

        if(teacherRepository.existsByEmail(req.getEmail())){
            throw new BadRequestException("Email đã tồn tại");
        }
        teacher.setEmail(req.getEmail());

        teacher.setPassword(req.getPassword());

        teacher.setCreatedAt(new Timestamp(System.currentTimeMillis()));

        teacher.setRoles(new ArrayList<>(Arrays.asList("TEACHER")));
        try{
            teacherRepository.save(teacher);
            return teacher;
        }
        catch (Exception e) {
            throw new InternalServerException("Lỗi khi thêm giảng viên") ;
        }
    }

    @Override
    public Page<Teacher> adminGetListTeacher(String name, String email, Integer page) {
        page--;
        if (page < 0) {
            page = 0;
        }
        Pageable pageable = PageRequest.of(page, Contant.LIMIT_USER, Sort.by("createdAt").descending());
        return teacherRepository.findByNameContainingOrEmail(name, email, pageable);
    }
}
