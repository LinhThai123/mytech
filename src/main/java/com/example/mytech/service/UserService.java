package com.example.mytech.service;

import com.example.mytech.entity.User;
import com.example.mytech.model.request.UserRep;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    // get list student
    List<User> getListUser () ;

    // create student
    public User createUser(UserRep rep);
}
