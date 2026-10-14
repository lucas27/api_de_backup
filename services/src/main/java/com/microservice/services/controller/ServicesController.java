package com.microservice.services.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.microservice.services.dto.StreamingFileDto;
import com.microservice.services.dto.UploadDto;
import com.microservice.services.service.Services;

import lombok.RequiredArgsConstructor;


@Controller
@RestController
@RequestMapping("/services")
// @RequiredArgsConstructor
public class ServicesController {
    private final Services service;
    
    public ServicesController(Services service) {
        this.service = service;
    }

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> upload(
        @RequestHeader("X-User-Id") String id,
        @RequestPart("file") MultipartFile file,
        @RequestParam("chunkIndex") Integer chunk, 
        @RequestParam("totalChunks") Integer total,
        @RequestPart("data") UploadDto uploadDto
    ) {
        System.out.print(id);
        String resp = service.uploadDataFile(id, file, chunk, total, uploadDto);
        return ResponseEntity.ok().body(resp);
    }

    @GetMapping(value="/streaming/{page}")
    public ResponseEntity<List<StreamingFileDto>> streaming(
        @RequestHeader("X-User-Id") String id,
        @PathVariable("page") Integer page
    ) {
        List<StreamingFileDto> responseDto = service.streamingFiles(id, page);
        service.streamingFiles(id, page);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }
}
