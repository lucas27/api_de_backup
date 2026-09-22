package com.microservice.services.controller;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.microservice.services.client.ServicesClient;
import com.microservice.services.dto.UploadDto;



@Controller
@RestController
@RequestMapping("/services") 
public class ServicesController {
    private ServicesClient client;

    public ServicesController(ServicesClient client) {
        this.client = client;
    }

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> upload(
        @RequestPart("file") MultipartFile file,
        @RequestParam("chunkIndex") Integer chunk, 
        @RequestParam("totalChunks") Integer total,
        @RequestPart("data") UploadDto uploadDto
    ) {
        String resp = client.storageFile(file, chunk, total, uploadDto);
        return ResponseEntity.ok().body(resp);
    }
}
