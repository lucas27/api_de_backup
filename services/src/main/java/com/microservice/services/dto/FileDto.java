package com.microservice.services.dto;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.UUID;

import com.microservice.services.enums.Mimetype;

public record FileDto(
    String name,
    String hash,
    Mimetype mimetype,
    Integer duration,
    BigInteger fileLength,
    String filePath,
    LocalDateTime createdFile
) {
    public static FileDto createDataFile(UploadDto dto) {
        return new FileDto(
            dto.name(), 
            UUID.randomUUID().toString(), 
            dto.mimetype(), 
            dto.duration(),  
            dto.fileLength(), 
            dto.filePath(), 
            dto.createdFile()
        );
    }
}
