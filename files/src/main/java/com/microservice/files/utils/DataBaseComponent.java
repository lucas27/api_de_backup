package com.microservice.files.utils;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.microservice.files.dto.request.FileDto;
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
    

    @Transactional(readOnly = true)
    public Page<FilesEntity> fileData(Integer page) {
        Pageable pageable = PageRequest.of(page, 10);
        return repository.findAll(pageable);
    }
}
