package com.example.mytech.service.impl;

import com.example.mytech.entity.ERole;
import com.example.mytech.entity.Role;
import com.example.mytech.entity.User;
import com.example.mytech.exception.BadRequestException;
import com.example.mytech.exception.InternalServerException;
import com.example.mytech.model.request.UserRep;
import com.example.mytech.repository.RoleRepository;
import com.example.mytech.repository.UserRepository;
import com.example.mytech.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.*;

@Component
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository ;

    @Override
    public List<User> getListUser() {
        return userRepository.findAll();
    }

    @Override
    public User createUser(UserRep rep) {
        User user = new User();

        user.setName(rep.getName());

        user.setPassword(rep.getPassword());

        // check trung email
        if (userRepository.existsByEmail(rep.getEmail())) {
            throw new BadRequestException("Email đã có trong hệ thống , Vui lòng chọn email khác");
        }
        user.setEmail(rep.getEmail());

        user.setPhone(rep.getPhone());

        user.setAddress(rep.getAddress());

        user.setCreatedAt(new Timestamp(System.currentTimeMillis()));

        user.setStatus(true);

        List<String> strRoles = rep.getRole();
        Set<Role> roles = new HashSet<>() ;

        if(strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER).orElseThrow(() -> new RuntimeException("Error : Role is not found."));
            roles.add(userRole) ;
        }
        else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin" :
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error : Role is not found"));
                        roles.add(adminRole);
                        break;

                    case "teacher" :
                        Role teacherRole = roleRepository.findByName(ERole.ROLE_TEACHER)
                                .orElseThrow(() -> new RuntimeException("Error : Role is not found"));
                        roles.add(teacherRole);
                        break;

                    default:
                        Role usersRole = roleRepository.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error : Role is not found"));
                        roles.add(usersRole);
                }
            });
        }
        try {
            userRepository.save(user);
            return user;
        } catch (Exception e) {
            throw new InternalServerException("Lỗi khi thêm học viên ");
        }
    }
}
