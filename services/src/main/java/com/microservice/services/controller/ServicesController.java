package com.microservice.services.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.microservice.services.dto.UploadDto;
import com.microservice.services.service.Services;

import lombok.RequiredArgsConstructor;


@Controller
@RestController
@RequestMapping("/services")
@RequiredArgsConstructor
public class ServicesController {
    private final Services service;
    
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> upload(
        @RequestPart("file") MultipartFile file,
        @RequestParam("chunkIndex") Integer chunk, 
        @RequestParam("totalChunks") Integer total,
        @RequestPart("data") UploadDto uploadDto
    ) {
        String resp = service.uploadDataFile(file, chunk, total, uploadDto);
        return ResponseEntity.ok().body(resp);
    }
}
