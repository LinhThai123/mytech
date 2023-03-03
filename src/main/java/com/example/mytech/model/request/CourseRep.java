package com.example.mytech.model.request;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;



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

    @JsonProperty("active")
    private Integer active ;

    private long price ;

    private Integer level ;

    private String image;

//    @JsonFormat(pattern = "yyyy-mm-dd", shape = JsonFormat.Shape.STRING)
    @JsonProperty("expired_at")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate expiredAt;

    private String teacher_id ;

    private String category_id;

}
