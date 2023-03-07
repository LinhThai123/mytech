package com.example.mytech.repository;

import com.example.mytech.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    boolean existsByEmail (String email);

    boolean existsByName (String name);

    User findByEmail (String email) ;

}
