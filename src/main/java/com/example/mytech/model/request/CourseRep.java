package com.example.mytech.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CourseRep {

    private String id ;
    @NotBlank(message = "Tên khóa học trống")
    @Size(min = 1, max = 300, message = "Độ dài tên sản phẩm từ 1 - 300 ký tự")
    private String name;

    @NotBlank(message = "Mô tả trống")
    private String description ;

    private boolean status ;

    private boolean isPublic ;

    private long price ;

    private Integer level ;

    private String image;

    private Timestamp createdAt;

    private Timestamp publishedAt;

    private Timestamp endAt;

    private String teacher_id ;

}
