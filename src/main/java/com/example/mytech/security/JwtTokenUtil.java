package com.example.mytech.security;

import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
@Component
public class JwtTokenUtil {

    private static final Logger logger = LoggerFactory.getLogger(JwtTokenUtil.class);

    // Token có hạn trong vòng 24 giờ kể từ thời điểm tạo, thời gian tính theo giây
    @Value("${jwt.duration}")
    public Integer duration;

    // Lấy giá trị key được cấu hình trong file appliacation.properties
    // Key này sẽ được sử dụng để mã hóa và giải mã
    @Value("${jwt.secret}")
    private String secret;

    // Sinh token
    public String generateToken(Authentication authentication) {

        CustomUserDetails principal = (CustomUserDetails) authentication.getPrincipal() ;

        String token = Jwts.builder()
                .setSubject(principal.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + duration * 1000))
                .signWith(SignatureAlgorithm.HS512, secret).compact();

        return token;
    }

    // Lấy thông tin được lưu trong token
    public String getUserNameFromJwtToken(String token) {
        // Kiểm tra token có bắt đầu bằng tiền tố
        if (token == null) return null;

        try {
            return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
        } catch (ExpiredJwtException e) {
            return null;
        }
    }

    public boolean validateJwtToken (String authToken) {
        try{
            Jwts.parser().setSigningKey(secret).parseClaimsJws(authToken);
            return true ;
        }catch (SignatureException e) {
            logger.error("Invalid JWT signture : {}" , e.getMessage());
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token : {}" , e.getMessage());
        } catch (ExpiredJwtException e ) {
            logger.error("Invalid JWT token is expired : {}" , e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("Invalid token is unsupported : {}" , e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claim string í empty : {}" , e.getMessage());
        }
        return false ;
    }
}
