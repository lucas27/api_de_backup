package com.microservice.files.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.microservice.files.dto.request.FileDto;

import lombok.RequiredArgsConstructor;

@Component 
@RequiredArgsConstructor  
public class FolderComponent {
    @Value("${api.folder-path}")
    private String absoluteFolderPath;

    public Map<String, Path> createFolder(Integer totalChunk, FileDto dto) throws IOException {
        String tempFolderPath = absoluteFolderPath + "/temp";
        String folderPath = absoluteFolderPath + "/" + dto.userName();
         
        Path tempPath = Paths.get(tempFolderPath);
        Path path = Paths.get(folderPath);

        if(!Files.exists(tempPath)) {
            Files.createDirectories(tempPath);
            Files.createDirectories(path);
        }

        return Map.of(
            "temp folder" , tempPath,
            "user folder", path
        );
    }

}
