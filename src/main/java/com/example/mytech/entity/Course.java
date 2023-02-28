package com.example.mytech.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "course")
public class Course {
    @GenericGenerator(name = "random_id", strategy = "com.example.mytech.model.custom.RandomIdGenerator")
    @Id
    @GeneratedValue(generator = "random_id")
    private String id;

    @Column(name = "name" , nullable = false)
    private String name ;

    @Column(name = "slug")
    private String slug;

    @Column(name = "description" , columnDefinition = "TEXT")
    private String description;

    @Column(name = "status" , nullable = false)
    private String status;

    @Column(name = "is_public", columnDefinition = "TINYINT(1)")
    private boolean isPublic;

    @Column (name = "price" , nullable = false)
    private double price ;

    @Column(name = "is_level" , nullable = false)
    private int level;

    @Column(name = "image")
    private String image;

    @Column(name = "created_at", nullable = false)
    private Timestamp createdAt;

    @Column(name = "modified_at", nullable = false)
    private Timestamp modifiedAt;

    @Column (name = "published_at")
    private Timestamp publishedAt;

    @Column(name = "end_at", nullable = false)
    private Timestamp endAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;



}
