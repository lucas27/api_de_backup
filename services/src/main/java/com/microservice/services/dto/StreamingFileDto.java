package com.microservice.services.dto;

import java.math.BigInteger;
import java.time.LocalDateTime;

import com.microservice.services.enums.Mimetype;


public record StreamingFileDto(
    String name,
    String miniatureFile,
    Mimetype mimetype,
    BigInteger fileLength,
    LocalDateTime createdFile,
    LocalDateTime createdAt
) {}