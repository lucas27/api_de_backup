package com.microservice.files.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/files")
public class FilesController {
    @RequestMapping(method = RequestMethod.POST, value="/teste")
    public void teste(@RequestParam("file") MultipartFile file) throws IOException {
        // Path text =  Paths.get(file.getOriginalFilename()).normalize().toAbsolutePath();
        // Files.copy(file.getInputStream(), text );
        // System.out.println(text);
        byte[] dados = file.getInputStream().readAllBytes();
        System.out.println(file.getSize());
        System.out.println(file.getBytes());
        System.out.println(file.getOriginalFilename());
        System.out.println(file.getContentType());
        // System.out.println(file.getInputStream());
    }
}
