package com.microservice.files.controller;

// import java.io.File;
// import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
// import java.nio.file.Files;
// import java.nio.file.Path;
// import java.nio.file.Paths;
import java.util.concurrent.CompletableFuture;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.microservice.files.dto.request.FileDto;
import com.microservice.files.dto.response.StreamingFileDto;
import com.microservice.files.entity.FilesEntity;
import com.microservice.files.service.FilesService;

import jakarta.servlet.ServletException;
// import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/file")
public class FilesController {
    private final FilesService service;

    FilesController(FilesService service) {
        this.service = service;
    }
    
    @RequestMapping(method = RequestMethod.POST, value="/files")
    public CompletableFuture<ResponseEntity<String>> file(
    // public ResponseEntity<String> file(
        @RequestPart("file") MultipartFile file,
        @RequestParam("chunkIndex") Integer chunk,
        @RequestParam("totalChunks") Integer total,
        @RequestPart("data") FileDto dto
        // ,
        // HttpServletRequest request
    ) throws IOException, ServletException {
        return service.uploadFiles(file, chunk, total, dto).thenApply(resp -> 
            ResponseEntity.status(HttpStatus.CREATED).body(resp)
        );
    }

    @RequestMapping(method = RequestMethod.GET, value="files")
    public List<StreamingFileDto> streaming(@RequestParam("page") Integer page, @RequestParam("user") Long userId) {
        return service.streamingFiles(page, userId);
    }
}
