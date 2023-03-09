package com.example.mytech.controller.admin;

import com.example.mytech.config.Contant;
import com.example.mytech.entity.User;
import com.example.mytech.repository.ImageRepository;
import com.example.mytech.security.CustomUserDetails;
import com.example.mytech.service.CourseService;
import com.example.mytech.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
public class ImageController {
    @Autowired
    private CourseService courseService;

    @Autowired
    private ImageRepository imageRepository ;

    @Autowired
    private ImageService imageService ;

    @PostMapping(value = "/api/upload-file")
    public ResponseEntity<?> uploadFileCourse(@RequestParam("file") MultipartFile file) {
        String path = imageService.uploadFile(file);
        return ResponseEntity.ok(path);
    }
//    @GetMapping(value = "/api/files/{fileId}", produces = MediaType.IMAGE_JPEG_VALUE)
//    public byte[] readFileCourse (@PathVariable String fileId) {
//        return imageService.readFile(fileId);
//    }

    @GetMapping("/api/files/{fileId}")
    public ResponseEntity<byte[]> getImage(@PathVariable String fileId) throws IOException {
        // Path to folder containing the uploaded image
        String folderPath = "src/main/resources/static/uploads";
        Path imagePath = Paths.get(folderPath, fileId);

        // Read the image file into a byte array
        byte[] imageBytes = Files.readAllBytes(imagePath);

        // Set the headers to return the image
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);

        // Return the image as a ResponseEntity with headers
        return new ResponseEntity<byte[]>(imageBytes, headers, HttpStatus.OK);
    }

}
