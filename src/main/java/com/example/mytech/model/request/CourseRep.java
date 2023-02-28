package com.example.mytech.model.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
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

    private Integer status ;

    private boolean isPublic ;

    private long price ;

    private Integer level ;

    private String image;

    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
    @JsonProperty("created_at")
    private Timestamp createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
    @JsonProperty("modified_at")
    private Timestamp modifiedAt;

    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
    @JsonProperty("published_at")
    private Timestamp publishedAt;

    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
    @JsonProperty("end_at")
    private Timestamp endAt;

    private String teacher_id ;

    private String category_id;

}
