package com.example.mytech.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TeacherRep {

    private String id ;

    @NotBlank(message = "Tên giảng viên trống")
    private String name ;

    @NotBlank(message = "Email trống ")
    @Email(message = "Email phải đúng định dạng")
    private String email;

    @NotBlank(message = "Mật khẩu trống")
    @Size(min = 4, max = 20, message = "Mật khẩu phải chứa từ 4 - 20 ký tự")
    private String password;

    @NotBlank(message = "Điện thoại trống")
    @Pattern(regexp="(09|01[2|6|8|9])+([0-9]{8})\\b", message = "Điện thoại không hợp lệ")
    private String phone ;

    @NotBlank(message = "Địa chỉ trống")
    private String address;

}
