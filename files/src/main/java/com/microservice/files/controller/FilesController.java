package com.microservice.files.controller;

// import java.io.File;
// import java.io.FileInputStream;
import java.io.IOException;
// import java.nio.file.Files;
// import java.nio.file.Path;
// import java.nio.file.Paths;
import java.util.concurrent.CompletableFuture;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.microservice.files.dto.FileDto;
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
        // System.out.println(dto);
        System.out.println(file);
        System.out.println(chunk);
        System.out.println(total);
        System.out.println(dto); 
        // for(var teste : request.getParts()) {
        //     System.out.println(teste.getName());
        // }
        return service.saveChunkFiles(file, chunk, total, dto).thenApply(resp -> 
            ResponseEntity.status(HttpStatus.CREATED).body(resp)
        );
    }
}
