package com.example.mytech.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "category")
public class Category {
    @GenericGenerator(name = "random_id", strategy = "com.example.mytech.model.custom.RandomIdGenerator")
    @Id
    @GeneratedValue(generator = "random_id")
    private String id;

    @Column(name = "name" , nullable = false)
    private String name ;

    @Column(name = "slug")
    private String slug ;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column (name = "modified_at")
    private Timestamp modifiedAt;
}
