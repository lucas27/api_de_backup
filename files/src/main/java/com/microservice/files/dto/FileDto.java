package com.microservice.files.dto;

import java.math.BigInteger;
import java.time.LocalDateTime;

import com.microservice.files.enums.Mimetype;

public record FileDto(
    String userId,
    String name,
    Mimetype mimetype,
    Integer duration,
    BigInteger fileLength,
    String filePath,
    LocalDateTime createdFile
) {}
