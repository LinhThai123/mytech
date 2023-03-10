package com.example.mytech.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

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

    @Column(name = "status" , nullable = false , columnDefinition = "TINYINT(1)")
    private int status;

    @Column(name = "active" ,columnDefinition = "TINYINT(1)")
    private int active;

    @Column (name = "price" , nullable = false)
    private long price ;

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

    @Column(name = "expired_at")
    private LocalDate expiredAt;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    @JsonBackReference
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;


}
