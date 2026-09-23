package com.microservice.services.dto;

import java.math.BigInteger;
import java.time.LocalDateTime;

import com.microservice.services.enums.Mimetype;

public record UploadDto(
    String name,
    Mimetype mimetype,
    Integer duration,
    BigInteger fileLength,
    String filePath,
    LocalDateTime createdFile
) {}
