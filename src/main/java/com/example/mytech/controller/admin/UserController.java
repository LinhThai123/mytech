package com.example.mytech.controller.admin;


import com.example.mytech.entity.ERole;
import com.example.mytech.entity.Role;
import com.example.mytech.entity.User;
import com.example.mytech.exception.MessageRespone;
import com.example.mytech.model.request.LoginRep;
import com.example.mytech.model.request.RegisterRep;
import com.example.mytech.model.response.JwtResponse;
import com.example.mytech.repository.RoleRepository;
import com.example.mytech.repository.UserRepository;
import com.example.mytech.security.CustomUserDetails;
import com.example.mytech.security.JwtTokenUtil;
import com.example.mytech.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleRepository roleRepository ;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    PasswordEncoder encoder;

//    @PostMapping("/api/admin/user")
//    public ResponseEntity<?> createStudent(@Valid @RequestBody UserRep req) {
//        User student = userService.createUser(req);
//        return ResponseEntity.ok(student);
//    }

    @PostMapping("/api/auth/login")
    public ResponseEntity<?> Login(@Validated @RequestBody LoginRep rep) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(rep.getEmail(), rep.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtil.generateToken(authentication);

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority()).collect(Collectors.toList());
        return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getName(), userDetails.getId() , userDetails.getUsername() , userDetails.getEmail(), roles));
    }

    @PostMapping("/api/auth/register")
    public ResponseEntity<?> Register(@Validated @RequestBody RegisterRep rep) {
        if (userRepository.existsByEmail(rep.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageRespone("Email đã tồn tại , Vui lòng chọn email khác"));
        }
        if (userRepository.existsByName(rep.getName())) {
            return ResponseEntity.badRequest().body(new MessageRespone("Username đã tồn tại , Vui lòng chọn username khác"));
        }

        User user = new User( rep.getName(), rep.getEmail(),
                encoder.encode(rep.getPassword()));

        Set<String> strRoles = rep.getRole();
        Set<Role> roles = new HashSet<>() ;

        if(strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER).orElseThrow(() -> new RuntimeException("Error : Role is not found."));
            roles.add(userRole) ;
        }
        else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin" :
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error : Role is not found"));
                        roles.add(adminRole);
                        break;

                    case "teacher" :
                        Role teacherRole = roleRepository.findByName(ERole.ROLE_TEACHER)
                                .orElseThrow(() -> new RuntimeException("Error : Role is not found"));
                        roles.add(teacherRole);
                        break;

                    default:
                        Role usersRole = roleRepository.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error : Role is not found"));
                        roles.add(usersRole);
                }
            });
        }
        user.setRoles(roles);
        userRepository.save(user);
        return ResponseEntity.ok(new MessageRespone("Đăng ký thành công"));
    }
}
