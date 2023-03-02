package com.example.mytech.model.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

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

    @JsonFormat(pattern = "yyyy-mm-dd hh:mm:ss", shape = JsonFormat.Shape.STRING)
    @JsonProperty("expired_at")
    private Timestamp expiredAt;

    private String teacher_id ;

    private String category_id;


    public Timestamp getExpiredAt() {
        return expiredAt;
    }

    public void setExpiredAt(String expiredAt) {
        this.expiredAt = Timestamp.valueOf(expiredAt) ;
    }
}
