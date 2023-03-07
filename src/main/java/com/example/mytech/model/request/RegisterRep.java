package com.example.mytech.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class RegisterRep {
    
    @NotBlank(message = "Họ tên trống")
    private String name ;

    @NotBlank(message = "Email trống")
    @Email(message = "Email không đúng định dạng")
    private String email;

    @NotBlank(message = "Mật khẩu trống")
    @Size(min = 6,max = 20, message = "Mật khẩu phải chứa từ 6-20 ký tự")
    private String password;

    private Set<String> role ;
}
