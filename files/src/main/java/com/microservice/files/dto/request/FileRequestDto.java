package com.microservice.files.dto.request;

import java.time.LocalDateTime;

import com.microservice.files.enums.Mimetype;

public record FileRequestDto(
    String name,
    Mimetype mimetype,
    Integer duration,
    String filePath,
    LocalDateTime createdFile
) {}
