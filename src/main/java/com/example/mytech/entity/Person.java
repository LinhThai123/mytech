package com.example.mytech.entity;

import com.vladmihalcea.hibernate.type.json.JsonStringType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
@TypeDef(
        name = "json",
        typeClass = JsonStringType.class
)
public abstract class Person {

    @Column
    private String name ;

    @Column(name = "email",nullable = false,length = 200)
    private String email;

    @Column(name = "gender")
    private String gender ;

    @Column(name = "password",nullable = false)
    private String password;

    @Column(name = "address")
    private String address;

    @Column(name = "phone")
    private String phone;

    @Column(name = "avatar")
    private String avatar;

    @Column(name = "status",columnDefinition = "BOOLEAN")
    private boolean status;

    @Type(type = "json")
    @Column(name = "roles" , nullable = false, columnDefinition = "json")
    private List<String> roles ;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "modified_at")
    private Timestamp modifiedAt;
}
