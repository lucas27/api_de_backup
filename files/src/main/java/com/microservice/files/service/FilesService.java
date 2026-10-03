package com.microservice.files.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.microservice.files.dto.FileDto;
import com.microservice.files.entity.FilesEntity;
import com.microservice.files.repository.FileRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor  
public class FilesService {
    @Value("${api.folder-path}")
    private String absoluteFolderPath;

    private final FileRepository repository;

    @Async
    public CompletableFuture<String> saveChunkFiles(MultipartFile file, Integer chunkIndex, Integer totalChunk, FileDto dto) throws IllegalStateException, IOException {
        // System.out.println(Thread.currentThread().getName()); 
        
        Path path = createFolder(totalChunk, dto);

        String resp = createFile(file, path, chunkIndex, dto);
        
        if(chunkIndex == totalChunk - 1) {
            saveDataBase(dto);
        }
        
        return CompletableFuture.completedFuture(
            resp 
        );
    }

    public Path createFolder(Integer totalChunk, FileDto dto) throws IOException {
        String nameFolder = totalChunk <= 1 ? "\\" + dto.userName() : "\\temp"; 
        String folderPath = absoluteFolderPath + nameFolder;
         
        Path path = Paths.get(folderPath);

        if(!Files.exists(path)) {
            Files.createDirectories(path);
        }

        return path;
    }

    public String createFile(MultipartFile file, Path folderPath, Integer chunkIndex, FileDto dto) throws IllegalStateException, IOException {
        String fileName = dto.name() + "_chunk_" + chunkIndex + "." + dto.mimetype().toString().toLowerCase();
        
        Path filePath = folderPath.resolve(fileName);
        
        if(Files.exists(filePath)) {
            return "O arquivo já existe";
        }

        file.transferTo(filePath.toFile());
        
        return "arquivo criado com sucesso"; 
    }


    @Transactional 
    public void saveDataBase(FileDto dto) {
        FilesEntity file = FilesEntity.saveFile(dto);
        repository.save(file);
    }
}
