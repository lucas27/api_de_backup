package com.microservice.files.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.microservice.files.dto.FileDto;

@Service 
public class FilesService {
    @Value("${api.folder-path}")
    private String folderPath;
 
    @Async
    public CompletableFuture<String> saveChunkFiles(MultipartFile file, Integer chunkIndex, FileDto dto) throws IllegalStateException, IOException {
        String fileName = dto.name() + "_chunk_" + chunkIndex + "." + dto.mimetype().toString().toLowerCase();
        // System.out.println(Thread.currentThread().getName()); 
        
        Path filePath = Paths.get(folderPath, fileName);
        
        if(Files.exists(filePath)) {
            return CompletableFuture.completedFuture(
                "O arquivo já existe"
            );
        }
        file.transferTo(filePath.toFile());
        
        return CompletableFuture.completedFuture(
            "arquivo criado com sucesso" 
        );
    }
}
