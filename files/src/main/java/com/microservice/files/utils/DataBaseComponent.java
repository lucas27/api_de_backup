package com.microservice.files.utils;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.microservice.files.dto.FileDto;
import com.microservice.files.entity.FilesEntity;
import com.microservice.files.repository.FileRepository;

import lombok.RequiredArgsConstructor;

@Component 
@RequiredArgsConstructor 
public class DataBaseComponent {

    private final FileRepository repository;

    @Transactional 
    public void saveDataBase(FileDto dto, String localPath) {
        FilesEntity file = FilesEntity.saveFile(dto, localPath);
        repository.save(file);
    }
    
}
