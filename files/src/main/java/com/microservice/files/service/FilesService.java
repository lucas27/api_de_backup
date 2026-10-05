package com.microservice.files.service;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.microservice.files.dto.FileDto;
import com.microservice.files.utils.FilesComponent;
import com.microservice.files.utils.FolderComponent;
import com.microservice.files.utils.DataBaseComponent;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor  
public class FilesService {

    private final FolderComponent createFolderComponent;

    private final FilesComponent createFilesComponent;

    private final DataBaseComponent dataBaseComponent;

    // foi a melhor forma de resolver o problema do async, ele vai adicionar o número de chunks.
    private static ArrayList<Integer> countIndex = new ArrayList<>();

    @Async
    public CompletableFuture<String> uploadFiles(MultipartFile file, Integer chunkIndex, Integer totalChunk, FileDto dto) throws IllegalStateException, IOException {
        String resp = null;

        Map<String, Path> folders = createFolderComponent.createFolder(totalChunk, dto);

        Path tempFolderPath = folders.get("temp folder");

        String definitivePath = folders.get("user folder").toString();

        resp = createFilesComponent.createChunkFiles(file, tempFolderPath, chunkIndex, totalChunk, dto);
        
        countIndex.add(chunkIndex);

        if(countIndex.size() == totalChunk) {
            resp = createFilesComponent.mergeChunkFiles(tempFolderPath.toString(), definitivePath, dto);
            
            dataBaseComponent.saveDataBase(dto, definitivePath);
            countIndex.clear();
        }
        
        return CompletableFuture.completedFuture(
            resp 
        );
    }
}
