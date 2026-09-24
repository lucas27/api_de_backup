package com.microservice.services.dto;

import java.math.BigInteger;
import java.time.LocalDateTime;

import com.microservice.services.enums.Mimetype;

public record FileDto(
    String name,
    Mimetype mimetype,
    Integer duration,
    BigInteger fileLength,
    String filePath,
    LocalDateTime createdFile
) {
    public static FileDto createDataFile(UploadDto dto) {
        return new FileDto(
            dto.name(), 
            dto.mimetype(), 
            dto.duration(),  
            dto.fileLength(), 
            dto.filePath(), 
            dto.createdFile()
        );
    }
}
