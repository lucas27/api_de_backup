package com.microservice.auth.dto.response;

public record TokenDto(
    String accessToken,
    String expiryToken,
    String refreshToken,
    String expiryRefreshToken
) {}
