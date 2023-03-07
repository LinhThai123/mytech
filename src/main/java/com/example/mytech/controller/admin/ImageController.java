package com.example.mytech.controller.admin;

import org.springframework.stereotype.Controller;

import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
public class ImageController {
    private final Path rootDir = Paths.get("src/main/resources/static/uploads");
}
