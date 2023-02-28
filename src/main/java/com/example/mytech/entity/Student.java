package com.example.mytech.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "student")
@Entity
public class Student extends Person{

    @GenericGenerator(name = "random_id", strategy = "com.example.mytech.model.custom.RandomIdGenerator")
    @Id
    @GeneratedValue(generator = "random_id")
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    private Course course;

}
