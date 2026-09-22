package com.microservice.services.dto;

import java.time.LocalDateTime;

import com.microservice.services.enums.Mimetype;

public record UploadDto(
    String name,
    Mimetype mimetype,
    Integer duration,
    String filePath,
    LocalDateTime createdFile
) {}
