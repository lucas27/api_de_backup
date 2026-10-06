package com.microservice.files.dto.response;

import java.math.BigInteger;
import java.time.LocalDateTime;

import com.microservice.files.entity.FilesEntity;
import com.microservice.files.enums.Mimetype;

public record StreamingFileDto(
    String name,
    Mimetype mimetype,
    BigInteger fileLength,
    LocalDateTime createdFile,
    LocalDateTime createdAt
) {
    public StreamingFileDto(FilesEntity file) {
        this(
            file.getName(), 
            file.getData().getMimetype(), 
            file.getData().getFileLength(), 
            file.getData().getCreatedFile(), 
            file.getCreatedAt()
        );
    }
}